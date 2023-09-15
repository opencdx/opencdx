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
import health.safe.api.opencdx.grpc.audit.AuditServiceGrpc;
import health.safe.api.opencdx.grpc.helloworld.GreeterGrpc;
import io.grpc.CallOptions;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ClientConfig.class})
class ClientConfigTest {

    ManagedChannel channel;
    CallOptions callOptions;

    @BeforeEach
    void setUp() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8000).build();
        this.callOptions = CallOptions.DEFAULT;
    }

    @AfterEach
    void tearDown() {
        this.channel.shutdownNow();
    }

    @Test
    void helloworldClientNegative() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withPropertyValues("opencdx.client.helloworld=false")
                .withUserConfiguration(ClientConfig.class);

        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> contextRunner.run(context -> {
                    HelloworldClient helloworldClient = context.getBean(HelloworldClient.class);
                }));
    }

    @Test
    void helloworldClientBlank() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withPropertyValues("opencdx.client.helloworld=")
                .withUserConfiguration(ClientConfig.class);

        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> contextRunner.run(context -> {
                    HelloworldClient helloworldClient = context.getBean(HelloworldClient.class);
                }));
    }

    @Test
    void helloworldClient() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner
                .withPropertyValues("opencdx.client.helloworld=true")
                .withUserConfiguration(ClientConfig.class)
                .withBean(GreeterGrpc.GreeterBlockingStub.class, this.channel, this.callOptions)
                .run(context -> {
                    HelloworldClient helloworldClient = context.getBean(HelloworldClient.class);
                    Assertions.assertNotNull(helloworldClient);
                });
    }

    @Test
    void auditClient() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner
                .withPropertyValues("opencdx.client.audit=true")
                .withUserConfiguration(ClientConfig.class)
                .withBean(AuditServiceGrpc.AuditServiceBlockingStub.class, this.channel, this.callOptions)
                .run(context -> {
                    OpenCDXAuditService auditClient = context.getBean(OpenCDXAuditService.class);
                    Assertions.assertNotNull(auditClient);
                });
    }
}
