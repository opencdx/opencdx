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
package cdx.opencdx.communications.config;

import cdx.opencdx.communications.service.OpenCDXEmailService;
import cdx.opencdx.communications.service.OpenCDXHTMLProcessor;
import cdx.opencdx.communications.service.OpenCDXSMSService;
import cdx.opencdx.communications.service.impl.OpenCDXEmailServiceImpl;
import cdx.opencdx.communications.service.impl.OpenCDXHTMLProcessorImpl;
import cdx.opencdx.communications.service.impl.OpenCDXSMSServiceImpl;
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
        // Explicit declaration to prevent this class from inadvertently being made instantiable
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
        return new MongoLockProvider(mongo.getDatabase("opencdx").getCollection("lock-" + applicationName));
    }
}