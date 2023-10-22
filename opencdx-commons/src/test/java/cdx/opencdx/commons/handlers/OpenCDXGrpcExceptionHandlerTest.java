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
package cdx.opencdx.commons.handlers;

import static org.junit.jupiter.api.Assertions.*;

import cdx.opencdx.commons.controllers.MockGrpcUnitTestController;
import cdx.opencdx.commons.services.InProcessService;
import health.safe.api.opencdx.grpc.unit.test.UnitTestGrpc;
import health.safe.api.opencdx.grpc.unit.test.UnitTestRequest;
import health.safe.api.opencdx.grpc.unit.test.UnitTestResponse;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@SpringBootTest(classes = OpenCDXGrpcExceptionHandler.class)
@ExtendWith(SpringExtension.class)
@SuppressWarnings("java:S5778")
class OpenCDXGrpcExceptionHandlerTest {

    private InProcessService<MockGrpcUnitTestController> inProcessService;

    private ManagedChannel channel;
    private UnitTestGrpc.UnitTestBlockingStub blockingStub;
    private UnitTestGrpc.UnitTestStub asyncStub;

    @BeforeEach
    void setUp() throws IOException, InstantiationException, IllegalAccessException {
        inProcessService = new InProcessService<MockGrpcUnitTestController>(MockGrpcUnitTestController.class);
        inProcessService.start();
        channel = InProcessChannelBuilder.forName("test")
                .directExecutor()
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        blockingStub = UnitTestGrpc.newBlockingStub(channel);
        asyncStub = UnitTestGrpc.newStub(channel);
    }

    @AfterEach
    void tearDown() {
        channel.shutdownNow();
        inProcessService.stop();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    void testA() throws InterruptedException {
        try {
            UnitTestResponse response = blockingStub.testA(
                    UnitTestRequest.newBuilder().setMessage("Test A").build());
            fail();
        } catch (StatusRuntimeException e) {
            log.error("Received \n{}", e.toString(), e);
            log.error("Cause: ", e.getStatus().getCause());
            log.error("Code: {}", e.getStatus().getCode());
            log.error("Description {}", e.getStatus().getDescription());
        } finally {
            shutdown();
        }
    }

    @Test
    void testB() throws InterruptedException {
        try {
            UnitTestResponse response = blockingStub.testB(
                    UnitTestRequest.newBuilder().setMessage("Test B").build());
            fail();
        } catch (StatusRuntimeException e) {
            log.error("Received \n{}", e.toString(), e);
            log.error("Cause: ", e.getStatus().getCause());
            log.error("Code: {}", e.getStatus().getCode());
            log.error("Description {}", e.getStatus().getDescription());
        } finally {
            shutdown();
        }
    }

    @Test
    void testC() throws InterruptedException {
        try {
            UnitTestResponse response = blockingStub.testC(
                    UnitTestRequest.newBuilder().setMessage("Test C").build());
            fail();
        } catch (StatusRuntimeException e) {
            log.error("Received \n{}", e.toString(), e);
            log.error("Cause: ", e.getStatus().getCause());
            log.error("Code: {}", e.getStatus().getCode());
            log.error("Description {}", e.getStatus().getDescription());
        } finally {
            shutdown();
        }
    }

    @Test
    void testD() throws InterruptedException {
        try {
            UnitTestResponse response = blockingStub.testD(
                    UnitTestRequest.newBuilder().setMessage("Test D").build());
            fail();
        } catch (StatusRuntimeException e) {
            log.error("Received \n{}", e.toString(), e);
            log.error("Cause:", e.getStatus().getCause());
            log.error("Code: {}", e.getStatus().getCode());
            log.error("Description {}", e.getStatus().getDescription());
        } finally {
            shutdown();
        }
    }
}
