/*
 * Copyright 2024 Safe Health Systems, Inc.
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
package cdx.opencdx.communications.config;

import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXMessageService;
import cdx.opencdx.communications.handlers.OpenCDXCDCMessageHandler;
import cdx.opencdx.communications.handlers.OpenCDXCommunicationNotificationMessageHandler;
import cdx.opencdx.communications.service.*;
import cdx.opencdx.communications.service.impl.OpenCDXEmailServiceImpl;
import cdx.opencdx.communications.service.impl.OpenCDXHTMLProcessorImpl;
import cdx.opencdx.communications.service.impl.OpenCDXSMSServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.mongo.MongoLockProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * Applicaiton Configuration
 */
@Slf4j
@Configuration
public class AppConfig {
    /**
     * Default Constructor
     */
    public AppConfig() {
        log.trace("Creating AppConfig");
    }

    /**
     * Bean to get SMS notification service.
     * @return Object of OpenCDXSMSServiceImpl.
     */
    @Bean
    @Description("Bean for OpenCDXSMSService, to get the SMS notification.")
    OpenCDXSMSService openCDXSMSService() {
        log.info("Creating OpenCDXSMSService");
        return new OpenCDXSMSServiceImpl();
    }

    /**
     * Bean to get HTML processor.
     * @return Object of OpenCDXHTMLProcessorImpl.
     */
    @Bean
    @Description("Bean for OpenCDXHTMLProcessor, to get the OpenCDX HTML template processed.")
    OpenCDXHTMLProcessor openCDXHTMLProcessor() {
        log.info("Creating OpenCDXHTMLProcessor");
        return new OpenCDXHTMLProcessorImpl();
    }

    @Bean
    @Description("Bean for OpenCDXEmailService, to send the Email Notification")
    OpenCDXEmailService openCDXEmailService() {
        log.info("Creating OpenCDXEmailService");
        return new OpenCDXEmailServiceImpl();
    }

    /**
     * Lock provider for synchronization between instances of a service
     * @param mongo MongoClient to contact Mongo.
     * @param applicationName Name of application for Collection
     * @return LockProvider to use
     */
    @Bean
    @Description("Bean for the LockProvider for service synchronization.")
    public LockProvider lockProvider(MongoClient mongo, @Value("${spring.application.name}") String applicationName) {
        log.info("Creating LockProvider");
        return new MongoLockProvider(mongo.getDatabase("opencdx").getCollection("lock-" + applicationName));
    }

    /**
     * Bean for Notification Message Handling
     * @param objectMapper ObjectMapper for converting bytes
     * @param openCDXNotificationService Notification Service for handling the Notification
     * @param openCDXMessageService Message Service for Subscribing.
     * @return the OpenCDXCommunicationNotificationMessageHandler bean
     */
    @Bean
    @Description(
            "OpenCDXCommunicationMessageHandler that is specific for handling communication messages for Notifications.")
    OpenCDXCommunicationNotificationMessageHandler openCDXCommunicationNotificationMessageHandler(
            ObjectMapper objectMapper,
            OpenCDXNotificationService openCDXNotificationService,
            OpenCDXMessageService openCDXMessageService,
            OpenCDXCurrentUser openCDXCurrentUser) {
        log.info("Creating OpenCDXCommunicationNotificationMessageHandler");
        return new OpenCDXCommunicationNotificationMessageHandler(
                objectMapper, openCDXNotificationService, openCDXMessageService, openCDXCurrentUser);
    }

    /**
     * Bean for CDC Message Handling
     * @param openCDXCDCMessageService Notification Service for handling the Notification
     * @param openCDXMessageService Message Service for Subscribing.
     * @return the OpenCDXCommunicationNotificationMessageHandler bean
     */
    @Bean
    @Description("OpenCDXCDCMessageHandler that is specific for handling CDC messages.")
    OpenCDXCDCMessageHandler openCDXCDCMessageHandler(
            OpenCDXCDCMessageService openCDXCDCMessageService,
            OpenCDXMessageService openCDXMessageService,
            OpenCDXCurrentUser openCDXCurrentUser) {
        log.info("Creating OpenCDXCDCMessageHandler");
        return new OpenCDXCDCMessageHandler(openCDXCDCMessageService, openCDXMessageService, openCDXCurrentUser);
    }
}
