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
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import health.safe.api.opencdx.commons.service.OpenCDXMessageService;
import health.safe.api.opencdx.commons.service.impl.NatsOpenCDXMessageServiceImpl;
import health.safe.api.opencdx.commons.service.impl.NoOpOpenCDXMessageServiceImpl;
import io.nats.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
     * Generates the Jackson Objectmapper with the JSON-to/from-Protobuf Message support.
     * @return ObjectMapper bean for use.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());

        return mapper;
    }

    @Bean("nats")
    @ConditionalOnProperty(prefix = "nats.spring", name = "server")
    OpenCDXMessageService natsOpenCDXMessageService(Connection natsConnection, ObjectMapper objectMapper) {
        return new NatsOpenCDXMessageServiceImpl(natsConnection, objectMapper);
    }

    @Bean("noop")
    @ConditionalOnMissingBean(OpenCDXMessageService.class)
    OpenCDXMessageService noOpOpenCDXMessageService() {
        return new NoOpOpenCDXMessageServiceImpl();
    }
}
