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
package health.safe.api.opencdx.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import health.safe.api.opencdx.commons.handlers.OpenCDXPerformanceHandler;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import health.safe.api.opencdx.commons.service.impl.NatsOpenCDXMessageServiceImpl;
import health.safe.api.opencdx.commons.service.impl.NoOpOpenCDXMessageServiceImpl;
import health.safe.api.opencdx.commons.service.impl.OpenCDXAuditServiceImpl;
import health.safe.api.opencdx.commons.templates.OpenCDXMongoAuditTemplate;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.nats.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

/**
 * Autoconfiguraiton class for opencdx-commons.
 */
@Slf4j
@AutoConfiguration
@Configuration
public class CommonsConfig {
    /**
     * Default Constructor
     */
    public CommonsConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /**
     * Observation Registry for the OpenCDX Performance Tracking.
     * @param observationRegistry Registry
     * @return ObservedAspect with performance handler.
     */
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        observationRegistry.observationConfig().observationHandler(new OpenCDXPerformanceHandler());
        return new ObservedAspect(observationRegistry);
    }

    /**
     * Generates the Jackson Objectmapper with the JSON-to/from-Protobuf Message support.
     * @return ObjectMapper bean for use.
     */
    @Bean
    @Primary
    @Description("Jackson ObjectMapper with all required registered modules.")
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    /**
     *  Used to convert Protobuf to JSON for MVC apis
     * @return  ProtobufHttpMessageConverter bean for use converting Protobuf to JSON for MVC apis
     */
    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    /**
     * OpenCDXMessageService Bean based on NATS
     * @param natsConnection NATS Connection
     * @param objectMapper Object Mapper to use.
     * @return OpenCDXMessageService to use for messaginging.
     */
    @Bean("nats")
    @Description("NATS implementation of the OpenCDXMessageService.")
    @Primary
    @ConditionalOnProperty(prefix = "nats.spring", name = "server")
    public OpenCDXMessageService natsOpenCDXMessageService(Connection natsConnection, ObjectMapper objectMapper) {
        return new NatsOpenCDXMessageServiceImpl(natsConnection, objectMapper);
    }

    @Bean("noop")
    @Description("The NOOP implementation of the OpenCDXMessage Service.")
    @ConditionalOnMissingBean(OpenCDXMessageService.class)
    OpenCDXMessageService noOpOpenCDXMessageService() {
        return new NoOpOpenCDXMessageServiceImpl();
    }

    @Bean
    @Description("OpenCDXOpenCDXAuditService for submitting audit messages through the message system.")
    OpenCDXAuditService openCDXAuditService(
            OpenCDXMessageService messageService, @Value("${spring.application.name}") String applicationName) {
        return new OpenCDXAuditServiceImpl(messageService, applicationName);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter) {
        return new MongoTemplate(mongoDbFactory, mongoConverter);
    }
}
