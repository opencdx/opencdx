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
package cdx.opencdx.tinkar.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import cdx.opencdx.grpc.tinkar.TinkarGetRequest;
import cdx.opencdx.grpc.tinkar.TinkarQueryRequest;
import cdx.opencdx.grpc.tinkar.TinkarQueryResponse;
import cdx.opencdx.grpc.tinkar.TinkarQueryResult;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXGrpcTinkarSearchControllerTest {

    OpenCDXGrpcTinkarSearchController openCDXGrpcTinkarSearchController;

    @Mock
    OpenCDXTinkarService openCDXTinkarService;

    @BeforeEach
    void setUp() {
        this.openCDXGrpcTinkarSearchController = new OpenCDXGrpcTinkarSearchController(openCDXTinkarService);
    }

    @Test
    void searchTinkar() {
        StreamObserver<TinkarQueryResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarQueryRequest request = TinkarQueryRequest.newBuilder()
                .setQuery("chronic disease of respiratory system")
                .setMaxResults(10)
                .build();
        TinkarQueryResponse response =
                TinkarQueryResponse.newBuilder().addResults(createResult()).build();

        when(openCDXTinkarService.search(request)).thenReturn(response);

        openCDXGrpcTinkarSearchController.searchTinkar(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getTinkarEntity() {
        StreamObserver<TinkarQueryResult> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarGetRequest request =
                TinkarGetRequest.newBuilder().setNid(-2144684618).build();
        TinkarQueryResult response = createResult();

        when(openCDXTinkarService.getEntity(request)).thenReturn(response);

        openCDXGrpcTinkarSearchController.getTinkarEntity(request, responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    private TinkarQueryResult createResult() {
        return TinkarQueryResult.newBuilder()
                .setNid(-2144684618)
                .setRcNid(-2147393046)
                .setPatternNid(-2147483638)
                .setFieldIndex(1)
                .setScore(13.158955F)
                .setHighlightedString("<B>Chronic</B> <B>disease</B> <B>of</B> <B>respiratory</B> <B>system</B>")
                .build();
    }
}
