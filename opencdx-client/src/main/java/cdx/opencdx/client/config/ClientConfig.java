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
import java.io.IOException;
import javax.net.ssl.SSLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides the Client configuration to create gRPC Clients to communicate with
 * gRPC based mircroservices.
 */
@Slf4j
@AutoConfiguration
@Configuration
@EnableFeignClients(basePackages = {"cdx.opencdx"})
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
    @ConditionalOnProperty(prefix = "opencdx.client.media", name = "enabled", havingValue = "true")
    OpenCDXMediaClient openCDXMediaClient(
            @Value("${opencdx.client.media.server}") String server, @Value("${opencdx.client.media.port}") Integer port)
            throws SSLException {
        return new OpenCDXMediaClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.anf", name = "enabled", havingValue = "true")
    OpenCDXANFClient openCDXANFClient(
            @Value("${opencdx.client.anf.server}") String server, @Value("${opencdx.client.anf.port}") Integer port)
            throws SSLException {
        return new OpenCDXANFClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.audit", name = "enabled", havingValue = "true")
    OpenCDXAuditClient openCDXAuditClient(
            @Value("${opencdx.client.audit.server}") String server, @Value("${opencdx.client.audit.port}") Integer port)
            throws SSLException {
        return new OpenCDXAuditClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.classification", name = "enabled", havingValue = "true")
    OpenCDXClassificationClient openCDXClassificationClient(
            @Value("${opencdx.client.classification.server}") String server,
            @Value("${opencdx.client.classification.port}") Integer port)
            throws SSLException {
        return new OpenCDXClassificationClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.communications", name = "enabled", havingValue = "true")
    OpenCDXCommunicationClient openCDXCommunicationClient(
            @Value("${opencdx.client.communication.server}") String server,
            @Value("${opencdx.client.communication.port}") Integer port)
            throws IOException {
        return new OpenCDXCommunicationClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.connected-test", name = "enabled", havingValue = "true")
    OpenCDXConnectedTestClient openCDXConnectedTestClient(
            @Value("${opencdx.client.connected-test.server}") String server,
            @Value("${opencdx.client.connected-test.port}") Integer port)
            throws SSLException {
        return new OpenCDXConnectedTestClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.connected-test", name = "enabled", havingValue = "true")
    OpenCDXCountryClient openCDXCountryClient(
            @Value("${opencdx.client.connected-test.server}") String server,
            @Value("${opencdx.client.connected-test.port}") Integer port)
            throws SSLException {
        return new OpenCDXCountryClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.connected-test", name = "enabled", havingValue = "true")
    OpenCDXDeviceClient openCDXDeviceClient(
            @Value("${opencdx.client.connected-test.server}") String server,
            @Value("${opencdx.client.connected-test.port}") Integer port)
            throws SSLException {
        return new OpenCDXDeviceClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.helloworld", name = "enabled", havingValue = "true")
    OpenCDXHelloworldClient openCDXHelloworldClient(
            @Value("${opencdx.client.helloworld.server}") String server,
            @Value("${opencdx.client.helloworld.port}") Integer port)
            throws SSLException {
        return new OpenCDXHelloworldClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.iam", name = "enabled", havingValue = "true")
    OpenCDXIAMOrganizationClient openCDXIAMOrganizationClient(
            @Value("${opencdx.client.iam.server}") String server, @Value("${opencdx.client.iam.port}") Integer port)
            throws SSLException {
        return new OpenCDXIAMOrganizationClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.iam", name = "enabled", havingValue = "true")
    OpenCDXIAMProfileClient openCDXIAMProfileClient(
            @Value("${opencdx.client.iam.server}") String server, @Value("${opencdx.client.iam.port}") Integer port)
            throws SSLException {
        return new OpenCDXIAMProfileClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.iam", name = "enabled", havingValue = "true")
    OpenCDXIAMUserClient openCDXIAMUserClient(
            @Value("${opencdx.client.iam.server}") String server, @Value("${opencdx.client.iam.port}") Integer port)
            throws SSLException {
        return new OpenCDXIAMUserClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.iam", name = "enabled", havingValue = "true")
    OpenCDXIAMWorkspaceClient openCDXIAMWorkspaceClient(
            @Value("${opencdx.client.iam.server}") String server, @Value("${opencdx.client.iam.port}") Integer port)
            throws SSLException {
        return new OpenCDXIAMWorkspaceClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.connected-test", name = "enabled", havingValue = "true")
    OpenCDXManufacturerClient openCDXManufacturerClient(
            @Value("${opencdx.client.connected-test.server}") String server,
            @Value("${opencdx.client.connected-test.port}") Integer port)
            throws SSLException {
        return new OpenCDXManufacturerClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.predictor", name = "enabled", havingValue = "true")
    OpenCDXPredictorClient openCDXPredictorClient(
            @Value("${opencdx.client.predictor.server}") String server,
            @Value("${opencdx.client.predictor.port}") Integer port)
            throws SSLException {
        return new OpenCDXPredictorClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.protector", name = "enabled", havingValue = "true")
    OpenCDXProtectorClient openCDXProtectorClient(
            @Value("${opencdx.client.protector.server}") String server,
            @Value("${opencdx.client.protector.port}") Integer port)
            throws SSLException {
        return new OpenCDXProtectorClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.provider", name = "enabled", havingValue = "true")
    OpenCDXProviderClient openCDXProviderClient(
            @Value("${opencdx.client.provider.server}") String server,
            @Value("${opencdx.client.provider.port}") Integer port)
            throws SSLException {
        return new OpenCDXProviderClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.questionnaire", name = "enabled", havingValue = "true")
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient(
            @Value("${opencdx.client.questionnaire.server}") String server,
            @Value("${opencdx.client.questionnaire.port}") Integer port)
            throws SSLException {
        return new OpenCDXQuestionnaireClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.routine", name = "enabled", havingValue = "true")
    OpenCDXRoutineClient openCDXRoutineClient(
            @Value("${opencdx.client.routine.server}") String server,
            @Value("${opencdx.client.routine.port}") Integer port)
            throws SSLException {
        return new OpenCDXRoutineClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.connected-test", name = "enabled", havingValue = "true")
    OpenCDXTestCaseClient openCDXTestCaseClient(
            @Value("${opencdx.client.connected-test.server}") String server,
            @Value("${opencdx.client.connected-test.port}") Integer port)
            throws SSLException {
        return new OpenCDXTestCaseClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.tinkar", name = "enabled", havingValue = "true")
    OpenCDXTinkarClient openCDXTinkarClient(
            @Value("${opencdx.client.tinkar.server}") String server,
            @Value("${opencdx.client.tinakr.port}") Integer port)
            throws SSLException {
        return new OpenCDXTinkarClientImpl(server, port);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.connected-test", name = "enabled", havingValue = "true")
    OpenCDXVendorClient openCDXVendorClient(
            @Value("${opencdx.client.connected-test.server}") String server,
            @Value("${opencdx.client.connected-test.port}") Integer port)
            throws SSLException {
        return new OpenCDXVendorClientImpl(server, port);
    }
}
