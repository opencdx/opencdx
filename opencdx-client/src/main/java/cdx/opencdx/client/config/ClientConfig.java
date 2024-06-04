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
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Provides the Client configuration to create gRPC Clients to communicate with
 * gRPC based microservices.
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

    /**
     * Creates an instance of ObservationGrpcClientInterceptor if the property "opencdx.client.tracing.enabled" is set to true.
     *
     * @param observationRegistry The observation registry to be used by the interceptor.
     *
     * @return An instance of ObservationGrpcClientInterceptor with the provided observation registry.
     */
    @Bean
    public ObservationGrpcClientInterceptor observationGrpcClientInterceptor(ObservationRegistry observationRegistry) {
        return new ObservationGrpcClientInterceptor(observationRegistry);
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.media", name = "enabled", havingValue = "true")
    OpenCDXMediaClient openCDXMediaClient(
            @Value("${opencdx.client.media.server}") String server,
            @Value("${opencdx.client.media.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXMediaClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.audit", name = "enabled", havingValue = "true")
    OpenCDXAuditClient openCDXAuditClient(
            @Value("${opencdx.client.audit.server}") String server,
            @Value("${opencdx.client.audit.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXAuditClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.classification", name = "enabled", havingValue = "true")
    OpenCDXClassificationClient openCDXClassificationClient(
            @Value("${opencdx.client.classification.server}") String server,
            @Value("${opencdx.client.classification.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXClassificationClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.communications", name = "enabled", havingValue = "true")
    OpenCDXCommunicationClient openCDXCommunicationClient(
            @Value("${opencdx.client.communication.server}") String server,
            @Value("${opencdx.client.communication.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws IOException {
        return new OpenCDXCommunicationClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXConnectedTestClient openCDXConnectedTestClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXConnectedTestClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.logistics", name = "enabled", havingValue = "true")
    OpenCDXCountryClient openCDXCountryClient(
            @Value("${opencdx.client.logistics.server}") String server,
            @Value("${opencdx.client.logistics.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXCountryClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.logistics", name = "enabled", havingValue = "true")
    OpenCDXDeviceClient openCDXDeviceClient(
            @Value("${opencdx.client.logistics.server}") String server,
            @Value("${opencdx.client.logistics.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXDeviceClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.helloworld", name = "enabled", havingValue = "true")
    OpenCDXHelloworldClient openCDXHelloworldClient(
            @Value("${opencdx.client.helloworld.server}") String server,
            @Value("${opencdx.client.helloworld.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXHelloworldClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.iam", name = "enabled", havingValue = "true")
    OpenCDXIAMOrganizationClient openCDXIAMOrganizationClient(
            @Value("${opencdx.client.iam.server}") String server,
            @Value("${opencdx.client.iam.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXIAMOrganizationClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.iam", name = "enabled", havingValue = "true")
    OpenCDXIAMProfileClient openCDXIAMProfileClient(
            @Value("${opencdx.client.iam.server}") String server,
            @Value("${opencdx.client.iam.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXIAMProfileClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.iam", name = "enabled", havingValue = "true")
    OpenCDXIAMWorkspaceClient openCDXIAMWorkspaceClient(
            @Value("${opencdx.client.iam.server}") String server,
            @Value("${opencdx.client.iam.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXIAMWorkspaceClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.logistics", name = "enabled", havingValue = "true")
    OpenCDXManufacturerClient openCDXManufacturerClient(
            @Value("${opencdx.client.logistics.server}") String server,
            @Value("${opencdx.client.logistics.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXManufacturerClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.predictor", name = "enabled", havingValue = "true")
    OpenCDXPredictorClient openCDXPredictorClient(
            @Value("${opencdx.client.predictor.server}") String server,
            @Value("${opencdx.client.predictor.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXPredictorClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.protector", name = "enabled", havingValue = "true")
    OpenCDXProtectorClient openCDXProtectorClient(
            @Value("${opencdx.client.protector.server}") String server,
            @Value("${opencdx.client.protector.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXProtectorClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.provider", name = "enabled", havingValue = "true")
    OpenCDXProviderClient openCDXProviderClient(
            @Value("${opencdx.client.provider.server}") String server,
            @Value("${opencdx.client.provider.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXProviderClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.questionnaire", name = "enabled", havingValue = "true")
    OpenCDXQuestionnaireClient openCDXQuestionnaireClient(
            @Value("${opencdx.client.questionnaire.server}") String server,
            @Value("${opencdx.client.questionnaire.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXQuestionnaireClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.routine", name = "enabled", havingValue = "true")
    OpenCDXRoutineClient openCDXRoutineClient(
            @Value("${opencdx.client.routine.server}") String server,
            @Value("${opencdx.client.routine.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXRoutineClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.logistics", name = "enabled", havingValue = "true")
    OpenCDXTestCaseClient openCDXTestCaseClient(
            @Value("${opencdx.client.logistics.server}") String server,
            @Value("${opencdx.client.logistics.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXTestCaseClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.tinkar", name = "enabled", havingValue = "true")
    OpenCDXTinkarClient openCDXTinkarClient(
            @Value("${opencdx.client.tinkar.server}") String server,
            @Value("${opencdx.client.tinkar.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXTinkarClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.logistics", name = "enabled", havingValue = "true")
    OpenCDXVendorClient openCDXVendorClient(
            @Value("${opencdx.client.logistics.server}") String server,
            @Value("${opencdx.client.logistics.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXVendorClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.connected-lab", name = "enabled", havingValue = "true")
    OpenCDXLabConnectedClient openCDXLabConnectedClient(
            @Value("${opencdx.client.connected-lab.server") String server,
            @Value("${opencdx.client.connected-lab.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXLabConnectedClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXHeightMeasurementClient openCDXHeightMeasurementClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXHeightMeasurementClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXWeightMeasurementClient openCDXWeightMeasurementClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXWeightMeasurementClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXBPMClient openCDXBPMClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXBPMClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXMedicationAdministrationClient openCDXMedicationAdministrationClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXMedicationAdministrationClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXMedicationClient openCDXMedicationClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXMedicationClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXHeartRPMClient openCDXHeartRPMClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXHeartRPMClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXVaccineClient openCDXVaccineClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXVaccineClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXAllergyClient openCDXAllergyClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXAllergyClientImpl(createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXDoctorNotesClient openCDXDoctorNotesClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXDoctorNotesClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    @Bean
    @ConditionalOnProperty(prefix = "opencdx.client.health", name = "enabled", havingValue = "true")
    OpenCDXTemperatureMeasurementClient openCDXTemperatureMeasurementClient(
            @Value("${opencdx.client.health.server}") String server,
            @Value("${opencdx.client.health.port}") Integer port,
            @Value("${opencdx.client.trustStore}") String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        return new OpenCDXTemperatureMeasurementClientImpl(
                createChannel(server, port, trustStore, observationGrpcClientInterceptor));
    }

    private ManagedChannel createChannel(
            String server,
            Integer port,
            String trustStore,
            ObservationGrpcClientInterceptor observationGrpcClientInterceptor)
            throws SSLException {
        try (InputStream certChain = new FileInputStream(trustStore)) {
            return NettyChannelBuilder.forAddress(server, port)
                    .intercept(observationGrpcClientInterceptor)
                    .useTransportSecurity()
                    .sslContext(
                            GrpcSslContexts.forClient().trustManager(certChain).build())
                    .build();
        } catch (IOException e) {
            throw new SSLException("Could not load certificate chain");
        }
    }
}
