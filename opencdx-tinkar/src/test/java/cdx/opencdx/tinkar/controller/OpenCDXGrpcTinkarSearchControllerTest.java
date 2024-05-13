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

import static org.mockito.Mockito.when;

import cdx.opencdx.grpc.service.tinkar.*;
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
    void testSearchTinkar() {
        StreamObserver<TinkarSearchQueryResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarSearchQueryRequest request = TinkarSearchQueryRequest.newBuilder()
                .setQuery("chronic disease of respiratory system")
                .setMaxResults(10)
                .build();
        TinkarSearchQueryResponse response = TinkarSearchQueryResponse.newBuilder()
                .addResults(createResult())
                .build();

        when(openCDXTinkarService.search(request)).thenReturn(response);

        openCDXGrpcTinkarSearchController.searchTinkar(request, responseObserver);

        Mockito.verify(openCDXTinkarService, Mockito.times(1)).search(request);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetTinkarEntity() {
        StreamObserver<TinkarGetResult> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarGetRequest request =
                TinkarGetRequest.newBuilder().setConceptId("ABED-1234").build();
        TinkarGetResult response =
                TinkarGetResult.newBuilder().setDescription("TEST").build();

        when(openCDXTinkarService.getEntity(request)).thenReturn(response);

        openCDXGrpcTinkarSearchController.getTinkarEntity(request, responseObserver);

        Mockito.verify(openCDXTinkarService, Mockito.times(1)).getEntity(request);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetTinkarChildConcepts() {
        StreamObserver<TinkarGetResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarGetRequest request = TinkarGetRequest.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .build();
        TinkarGetResult result =
                TinkarGetResult.newBuilder().setDescription("TEST").build();
        TinkarGetResponse response =
                TinkarGetResponse.newBuilder().addResults(result).build();

        when(openCDXTinkarService.getTinkarChildConcepts(request)).thenReturn(response);

        openCDXGrpcTinkarSearchController.getTinkarChildConcepts(request, responseObserver);

        Mockito.verify(openCDXTinkarService, Mockito.times(1)).getTinkarChildConcepts(request);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetTinkarDescendantConcepts() {
        StreamObserver<TinkarGetResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarGetRequest request = TinkarGetRequest.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .build();
        TinkarGetResult result =
                TinkarGetResult.newBuilder().setDescription("TEST").build();
        TinkarGetResponse response =
                TinkarGetResponse.newBuilder().addResults(result).build();

        when(openCDXTinkarService.getTinkarDescendantConcepts(request)).thenReturn(response);

        openCDXGrpcTinkarSearchController.getTinkarDescendantConcepts(request, responseObserver);

        Mockito.verify(openCDXTinkarService, Mockito.times(1)).getTinkarDescendantConcepts(request);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetLIDRRecordConceptsFromTestKit() {
        StreamObserver<TinkarGetResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarGetRequest request = TinkarGetRequest.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .build();
        TinkarGetResult result =
                TinkarGetResult.newBuilder().setDescription("TEST").build();
        TinkarGetResponse response =
                TinkarGetResponse.newBuilder().addResults(result).build();

        when(openCDXTinkarService.getLIDRRecordConceptsFromTestKit(request)).thenReturn(response);

        openCDXGrpcTinkarSearchController.getLIDRRecordConceptsFromTestKit(request, responseObserver);

        Mockito.verify(openCDXTinkarService, Mockito.times(1)).getLIDRRecordConceptsFromTestKit(request);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetResultConformanceConceptsFromLIDRRecord() {
        StreamObserver<TinkarGetResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarGetRequest request = TinkarGetRequest.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .build();
        TinkarGetResult result =
                TinkarGetResult.newBuilder().setDescription("TEST").build();
        TinkarGetResponse response =
                TinkarGetResponse.newBuilder().addResults(result).build();

        when(openCDXTinkarService.getResultConformanceConceptsFromLIDRRecord(request))
                .thenReturn(response);

        openCDXGrpcTinkarSearchController.getResultConformanceConceptsFromLIDRRecord(request, responseObserver);

        Mockito.verify(openCDXTinkarService, Mockito.times(1)).getResultConformanceConceptsFromLIDRRecord(request);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void testGetAllowedResultConceptsFromResultConformance() {
        StreamObserver<TinkarGetResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TinkarGetRequest request = TinkarGetRequest.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .build();
        TinkarGetResult result =
                TinkarGetResult.newBuilder().setDescription("TEST").build();
        TinkarGetResponse response =
                TinkarGetResponse.newBuilder().addResults(result).build();

        when(openCDXTinkarService.getAllowedResultConceptsFromResultConformance(request))
                .thenReturn(response);

        openCDXGrpcTinkarSearchController.getAllowedResultConceptsFromResultConformance(request, responseObserver);

        Mockito.verify(openCDXTinkarService, Mockito.times(1)).getAllowedResultConceptsFromResultConformance(request);
        Mockito.verify(responseObserver, Mockito.times(1)).onNext(response);
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    private TinkarSearchQueryResult createResult() {
        return TinkarSearchQueryResult.newBuilder()
                .setNid(-2144684618)
                .setRcNid(-2147393046)
                .setPatternNid(-2147483638)
                .setFieldIndex(1)
                .setScore(13.158955F)
                .setHighlightedString("<B>Chronic</B> <B>disease</B> <B>of</B> <B>respiratory</B> <B>system</B>")
                .build();
    }
}
