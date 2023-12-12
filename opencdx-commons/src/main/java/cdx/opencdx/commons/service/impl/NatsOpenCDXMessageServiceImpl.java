/*
 * Copyright 2023 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.commons.service.impl;

import cdx.opencdx.commons.annotations.RetryAnnotation;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.handlers.OpenCDXMessageHandler;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import io.nats.client.*;
import io.nats.client.api.*;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * NATS based implementation of OpenCDXMessageService
 */
@Slf4j
@Observed(name = "opencdx")
public class NatsOpenCDXMessageServiceImpl implements OpenCDXMessageService {

    private static final String DOMAIN = "NatsOpenCDXMessageServiceImpl";
    private static final String OPENCDX = "opencdx";
    private final Connection natsConnection;

    private final ObjectMapper objectMapper;
    private final String applicationName;
    private final Map<String, JetStreamSubscription> subscriptionMap;

    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for setting up NATS based OpenCDXMessageService
     * @param natsConnection NATS Connection
     * @param objectMapper Jackson Object Mapper
     * @param applicationName Name of the application
     * @param openCDXCurrentUser System for setting the current user
     */
    public NatsOpenCDXMessageServiceImpl(
            Connection natsConnection,
            ObjectMapper objectMapper,
            @Value("${spring.application.name}") String applicationName,
            OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.subscriptionMap = new HashMap<>();
        this.applicationName = applicationName;
        this.natsConnection = natsConnection;
        this.objectMapper = objectMapper;

        try {
            JetStreamManagement jetStreamManagement = this.natsConnection.jetStreamManagement();
            StreamConfiguration configuration = StreamConfiguration.builder()
                    .name(OPENCDX)
                    .subjects(
                            OpenCDXMessageService.AUDIT_MESSAGE_SUBJECT,
                            OpenCDXMessageService.NOTIFICATION_MESSAGE_SUBJECT)
                    .maxAge(Duration.ofDays(7))
                    .maxConsumers(10)
                    .storageType(StorageType.File)
                    .noAck(false)
                    .build();

            StreamInfo streamInfo = jetStreamManagement.addStream(configuration);

            if (streamInfo != null) {
                log.info("JetStream created successfully: "
                        + streamInfo.getConfiguration().getName());
            } else {
                throw new OpenCDXInternal(DOMAIN, 3, "Failed to create JetStream");
            }
        } catch (JetStreamApiException | IOException e) {
            throw new OpenCDXInternal(DOMAIN, 2, "Failed to create JetStream", e);
        }
    }

    @Override
    @RetryAnnotation
    public void subscribe(String subject, OpenCDXMessageHandler handler) {
        log.info("Subscribing to: {}", subject);
        PushSubscribeOptions subscribeOptions = PushSubscribeOptions.builder().stream(OPENCDX)
                .durable(this.applicationName + "_" + subject.replace(".", "_"))
                .build();

        try {

            JetStreamSubscription subscription = natsConnection
                    .jetStream()
                    .subscribe(
                            subject,
                            this.natsConnection.createDispatcher(),
                            new NatsMessageHandler(handler, this.openCDXCurrentUser),
                            true,
                            subscribeOptions);

            if (subscription != null) {
                log.info(
                        "Queue: {} Subject: {} Active: {}",
                        subscription.getQueueName(),
                        subscription.getSubject(),
                        subscription.isActive());
            }
            this.subscriptionMap.put(subject, subscription);
        } catch (IOException | JetStreamApiException e) {
            throw new OpenCDXInternal(DOMAIN, 2, "Failed JetStream Subscribe", e);
        }
    }

    @Override
    @RetryAnnotation
    public void unSubscribe(String subject) {
        JetStreamSubscription jetStreamSubscription = this.subscriptionMap.get(subject);
        if (jetStreamSubscription != null && jetStreamSubscription.isActive()) {
            jetStreamSubscription.unsubscribe();
        }
        this.subscriptionMap.remove(subject);
    }

    @Override
    @RetryAnnotation
    public void send(String subject, Object object) {

        try {
            natsConnection.jetStream().publish(subject, this.objectMapper.writeValueAsBytes(object));
        } catch (IOException | JetStreamApiException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, "Failed NATS Publish on: " + object.toString(), e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Subject", subject);
            openCDXNotAcceptable.getMetaData().put("Object", object.toString());
            throw openCDXNotAcceptable;
        }
    }

    /**
     * Handler wrapper for NATS
     */
    @Slf4j
    protected static class NatsMessageHandler implements MessageHandler {
        OpenCDXMessageHandler handler;

        private final OpenCDXCurrentUser openCDXCurrentUser;

        /**
         * Constructor for wrapping a OpenCDXMessageHandler
         *
         * @param handler            OpenCDXMessageHandler to wrap.
         * @param openCDXCurrentUser
         */
        protected NatsMessageHandler(OpenCDXMessageHandler handler, OpenCDXCurrentUser openCDXCurrentUser) {
            this.handler = handler;
            this.openCDXCurrentUser = openCDXCurrentUser;
        }

        @Override
        public void onMessage(Message msg) {
            this.openCDXCurrentUser.configureAuthentication("SYSTEM");
            log.debug("Received Message: {}", msg);
            handler.receivedMessage(msg.getData());
        }
    }
}
