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
package cdx.opencdx.client.config;

import cdx.opencdx.client.service.*;
import cdx.opencdx.client.service.impl.*;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.ObservationRegistry;
import javax.net.ssl.SSLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Provides the Client configuration to create gRPC Clients to communicate with
 * gRPC based mircroservices.
 */
@Slf4j
@AutoConfiguration
@Configuration
public class ClientConfig {
    /**
     * Default Constructor
     */
    public ClientConfig() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.tracing", name = "enabled", havingValue = "true")
    public ObservationGrpcClientInterceptor observationGrpcClientInterceptor(ObservationRegistry observationRegistry) {
        return new ObservationGrpcClientInterceptor(observationRegistry);
    }

    @Bean
    @Description("Web client for Media upload/download")
    OpenCDXMediaUpDownClient mediaUpDown() {

        WebClient mediaUpDownWebClient =
                WebClient.builder().baseUrl("$(opencdx.client.mediaUoDown").build();
        return new OpenCDXMediaUpDownClientImpl(mediaUpDownWebClient);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.media", name = "enabled", havingValue = "true")
    OpenCDXMediaClient openCDXMediaClient(
            @Value("${opencdx.client.media.server}") String server, @Value("${opencdx.client.media.port}") Integer port)
            throws SSLException {
        return new OpenCDXMediaClientImpl(server, port);
    }
}
