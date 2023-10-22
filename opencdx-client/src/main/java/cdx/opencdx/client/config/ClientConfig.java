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
package cdx.opencdx.client.config;

import cdx.media.v2alpha.MediaServiceGrpc;
import cdx.open_audit.v2alpha.AuditServiceGrpc;
import cdx.open_communication.v2alpha.CommunicationServiceGrpc;
import cdx.opencdx.client.service.*;
import cdx.opencdx.client.service.impl.*;
import health.safe.api.opencdx.grpc.helloworld.GreeterGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
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
    @Description("Web client for Media upload/download")
    OpenCDXMediaUpDownClient mediaUpDown() {
        WebClient mediaUpDownWebClient =
                WebClient.builder().baseUrl("$(opencdx.client.mediaUoDown").build();
        return new OpenCDXMediaUpDownClientImpl(mediaUpDownWebClient);
    }

    @Bean
    @Description("gRPC Client for HelloWorld")
    @ConditionalOnProperty(prefix = "opencdx.client", name = "helloworld", havingValue = "true")
    OpenCDXHelloworldClient helloworldClient(
            @GrpcClient("helloworld-server") GreeterGrpc.GreeterBlockingStub greeterBlockingStub) {
        return new OpenCDXHelloworldClientImpl(greeterBlockingStub);
    }

    @Bean
    @Description("gRPC Client for Audit")
    @ConditionalOnProperty(prefix = "opencdx.client", name = "audit", havingValue = "true")
    OpenCDXAuditClient auditClient(
            @Value("${spring.application.name}") String applicationName,
            @GrpcClient("audit-service") AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub) {
        return new OpenCDXAuditClientImpl(applicationName, auditServiceBlockingStub);
    }

    @Bean
    @Description("gRPC Client for Media")
    @ConditionalOnProperty(prefix = "opencdx.client", name = "media", havingValue = "true")
    OpenCDXMediaClient mediaClient(
            @GrpcClient("media-service") MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub) {
        return new OpenCDXMediaClientImpl(mediaServiceBlockingStub);
    }

    @Bean
    @Description("gRPC Client for Communication")
    @ConditionalOnProperty(prefix = "opencdx.client", name = "communication", havingValue = "true")
    OpenCDXCommunicationClient communicationClient(
            @Value("${spring.application.name}") String applicationName,
            @GrpcClient("communicaton-service")
                    CommunicationServiceGrpc.CommunicationServiceBlockingStub communicationServiceBlockingStub) {
        return new OpenCDXCommunicationClientImpl(communicationServiceBlockingStub);
    }
}
