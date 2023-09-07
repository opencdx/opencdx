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
package health.safe.api.opencdx.commons.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import health.safe.api.opencdx.commons.handlers.OpenCDXMessageHandler;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * NATS based implementation of OpenCDXMessageService
 */
@Slf4j
@ConditionalOnProperty(prefix = "nats.spring", name = "server")
@Service("NATS")
public class NatsServiceImplOpenCDX implements OpenCDXMessageService {

    private final Connection natsConnection;
    private final Dispatcher dispatcher;

    private final ObjectMapper objectMapper;

    /**
     * Constructor for setting up NATS based OpenCDXMessageService
     * @param natsConnection NATS Connection
     * @param objectMapper Jackson Object Mapper
     */
    public NatsServiceImplOpenCDX(Connection natsConnection, ObjectMapper objectMapper) {
        this.natsConnection = natsConnection;
        this.objectMapper = objectMapper;
        this.dispatcher = this.natsConnection.createDispatcher();
    }

    @Override
    public void subscribe(String subject, OpenCDXMessageHandler handler) {
        log.info("Subscribing to: {}", subject);

        this.dispatcher.subscribe(subject, new NatsMessageHandler(handler));
    }

    @Override
    public void unSubscribe(String subject) {
        this.dispatcher.unsubscribe(subject);
    }

    @Override
    public void send(String subject, Object object) {

        try {
            natsConnection.publish(subject, this.objectMapper.writeValueAsBytes(object));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable = new OpenCDXNotAcceptable(
                    "NatsServiceImplOpenCDX", 1, "Failed NATS Publish on: " + object.toString(), e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Subject", subject);
            openCDXNotAcceptable.getMetaData().put("Object", object.toString());
            throw openCDXNotAcceptable;
        }
    }

    /**
     * Handler wrapper for NATS
     */
    protected class NatsMessageHandler implements MessageHandler {
        OpenCDXMessageHandler handler;

        /**
         * Constructor for wrapping a OpenCDXMessageHandler
         * @param handler OpenCDXMessageHandler to wrap.
         */
        protected NatsMessageHandler(OpenCDXMessageHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onMessage(Message msg) throws InterruptedException {
            handler.receivedMessage(msg.getData());
        }
    }
}
