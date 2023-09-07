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
package health.safe.api.opencdx.tinkar;

import health.safe.api.opencdx.tinkar.config.AppConfig;
import io.nats.client.Connection;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTests {
    @Autowired
    AppConfig appConfig;

    @InjectMocks
    private ServiceInstanceRestController controller;

    @Mock
    private DiscoveryClient discoveryClient;

    @MockBean
    Connection connection;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(appConfig);
    }

    @Test
    void testApplication() {
        MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class);
        mocked.when((MockedStatic.Verification) SpringApplication.run(Application.class, new String[] {}))
                .thenReturn(null);
        Application.main(new String[] {});
        mocked.verify(() -> SpringApplication.run(Application.class, new String[] {}));
    }

    @Test
    void serviceInstanceRestController() {
        ServiceInstance si = Mockito.mock(ServiceInstance.class);
        Mockito.when(si.getUri()).thenReturn(URI.create("myUri"));
        Mockito.when(discoveryClient.getInstances(Mockito.anyString())).thenReturn(List.of(si));

        Assertions.assertEquals(List.of(si), controller.serviceInstancesByApplicationName("TestApp"));
    }
}
