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
package health.safe.api.opencdx.client.service.impl;

import health.safe.api.opencdx.client.exceptions.OpenCDXClientException;
import health.safe.api.opencdx.client.service.OpenCDXHelloworldClient;
import health.safe.api.opencdx.grpc.helloworld.GreeterGrpc;
import health.safe.api.opencdx.grpc.helloworld.HelloReply;
import health.safe.api.opencdx.grpc.helloworld.HelloRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class OpenCDXHelloworldClientImplTest {

    @Mock
    GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

    OpenCDXHelloworldClient openCDXHelloworldClient;

    @BeforeEach
    void setUp() {
        this.greeterBlockingStub = Mockito.mock(GreeterGrpc.GreeterBlockingStub.class);
        this.openCDXHelloworldClient = new OpenCDXHelloworldClientImpl(this.greeterBlockingStub);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.greeterBlockingStub);
    }

    @Test
    void sayHello() {
        Mockito.when(this.greeterBlockingStub.sayHello(Mockito.any(HelloRequest.class)))
                .thenReturn(HelloReply.newBuilder().setMessage("Hello Bob!").build());

        Assertions.assertEquals("Hello Bob!", this.openCDXHelloworldClient.sayHello("Bob"));
    }

    @Test
    void sayHelloException() {
        Mockito.when(this.greeterBlockingStub.sayHello(Mockito.any(HelloRequest.class)))
                .thenThrow(new StatusRuntimeException(Status.INTERNAL));

        Assertions.assertThrows(OpenCDXClientException.class, () -> this.openCDXHelloworldClient.sayHello("Bob"));
    }
}
