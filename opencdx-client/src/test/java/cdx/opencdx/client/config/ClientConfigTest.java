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

import static org.junit.jupiter.api.Assertions.*;

import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import io.micrometer.observation.ObservationRegistry;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

class ClientConfigTest {

    @MockBean
    ObservationGrpcClientInterceptor observationGrpcClientInterceptor;

    @MockBean
    ObservationRegistry observationRegistry;

    @Test
    void openCDXANFClient() throws IOException, NoSuchFieldException, IllegalAccessException {
        this.observationGrpcClientInterceptor = Mockito.mock(ObservationGrpcClientInterceptor.class);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.observationGrpcClientInterceptor(observationRegistry);
        clientConfig.openCDXANFClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXAuditClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXClassificationClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXCommunicationClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXHelloworldClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXIAMOrganizationClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXIAMProfileClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXIAMUserClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXIAMWorkspaceClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXPredictorClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXProtectorClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXProviderClient("server", 9090, observationGrpcClientInterceptor);
        clientConfig.openCDXRoutineClient("server", 9090, observationGrpcClientInterceptor);
        Assertions.assertNotNull(clientConfig.openCDXTinkarClient("server", 9090, observationGrpcClientInterceptor));
    }
}
