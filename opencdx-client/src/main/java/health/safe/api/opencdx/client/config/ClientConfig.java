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
package health.safe.api.opencdx.client.config;

import health.safe.api.opencdx.client.service.HelloworldClient;
import health.safe.api.opencdx.client.service.OpenCDXAuditService;
import health.safe.api.opencdx.client.service.impl.HelloworldClientImpl;
import health.safe.api.opencdx.client.service.impl.OpenCDXAuditServiceImpl;
import health.safe.api.opencdx.grpc.audit.AuditServiceGrpc;
import health.safe.api.opencdx.grpc.helloworld.GreeterGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

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
    @Description("gRPC Client for HelloWorld")
    @ConditionalOnProperty(prefix = "opencdx.client", name = "helloworld", havingValue = "true")
    HelloworldClient helloworldClient(
            @GrpcClient("helloworld-server") GreeterGrpc.GreeterBlockingStub greeterBlockingStub) {
        return new HelloworldClientImpl(greeterBlockingStub);
    }

    @Bean
    @Description("gRPC Client for Audit")
    @ConditionalOnProperty(prefix = "opencdx.client", name = "audit", havingValue = "true")
    OpenCDXAuditService auditClient(
            @Value("${spring.application.name}") String applicationName,
            @GrpcClient("audit-service") AuditServiceGrpc.AuditServiceBlockingStub auditServiceBlockingStub) {
        return new OpenCDXAuditServiceImpl(applicationName, auditServiceBlockingStub);
    }
}
