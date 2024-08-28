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
package cdx.opencdx.tinkar.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.grpc.service.tinkar.*;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import cdx.opencdx.tinkar.service.TinkarPrimitive;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.id.PublicIds;
import dev.ikm.tinkar.common.service.CachingService;
import dev.ikm.tinkar.common.service.PrimitiveData;
import dev.ikm.tinkar.common.service.PrimitiveDataSearchResult;
import dev.ikm.tinkar.common.service.ServiceProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXTinkarServiceImplTest {

    OpenCDXTinkarService openCDXTinkarService;

    @Mock
    TinkarPrimitive primitiveDataService;

    @BeforeEach
    void setUp() {
        openCDXTinkarService = new OpenCDXTinkarServiceImpl("parent", "child", primitiveDataService);
    }

    @Test
    void testSearch() throws Exception {
        Mockito.when(this.primitiveDataService.search("test", 10)).thenReturn(createSearchResults());

        TinkarSearchQueryResponse response = openCDXTinkarService.search(TinkarSearchQueryRequest.newBuilder()
                .setQuery("test")
                .setMaxResults(10)
                .build());

        assertNotNull(response);
        Assertions.assertEquals(1, response.getResultsCount());

        Mockito.verify(this.primitiveDataService).search("test", 10);
    }

    @Test
    void testSearchException() throws Exception {
        Mockito.when(this.primitiveDataService.search(Mockito.anyString(), Mockito.any(Integer.class)))
                .thenThrow(new OpenCDXInternal("TinkarPrimitiveImpl", 1, "Error searching for primitive data"));

        try {
            openCDXTinkarService.search(TinkarSearchQueryRequest.newBuilder()
                    .setQuery("test")
                    .setMaxResults(10)
                    .build());
        } catch (Exception e) {
            Assertions.assertEquals(OpenCDXBadRequest.class, e.getClass());
            Assertions.assertEquals("Search Failed", e.getMessage());
        }

        verify(primitiveDataService).search("test", 10);
    }

    @Test
    void testGetEntity() throws Exception {
        Mockito.when(this.primitiveDataService.descriptionsOf(Mockito.any(List.class)))
                .thenReturn(List.of("TEST"));

        TinkarGetResult result = openCDXTinkarService.getEntity(TinkarGetRequest.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .build());

        assertNotNull(result);
        Assertions.assertEquals("TEST", result.getDescription());

        Mockito.verify(this.primitiveDataService).descriptionsOf(Mockito.any(List.class));
    }

    @Test
    void testGetEntityException() throws Exception {

        Mockito.when(this.primitiveDataService.descriptionsOf(Mockito.any(List.class)))
                .thenReturn(new ArrayList<String>());
        try {
            TinkarGetResult result = openCDXTinkarService.getEntity(TinkarGetRequest.newBuilder()
                    .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                    .build());
        } catch (Exception e) {
            Assertions.assertEquals(OpenCDXBadRequest.class, e.getClass());
            Assertions.assertEquals("Entity Get Failed", e.getMessage());
        }

        Mockito.verify(this.primitiveDataService).descriptionsOf(Mockito.any(List.class));
    }

    @Test
    void testGetTinkarChildConcepts() {

        Mockito.when(this.primitiveDataService.childrenOf(Mockito.any(PublicId.class)))
                .thenReturn(List.of(
                        PublicIds.of(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")),
                        PublicIds.of(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"))));

        Mockito.when(this.primitiveDataService.descriptionsOf(Mockito.any(List.class)))
                .thenReturn(List.of("TEST-CHILD-DESCRIPTION"));

        TinkarGetResponse childConcepts = openCDXTinkarService.getTinkarChildConcepts(TinkarGetRequest.newBuilder()
                .setConceptId("23e07078-f1e2-3f6a-9b7a-9397bcd91cfe")
                .build());
        assertNotNull(childConcepts);
        Assertions.assertEquals(2, childConcepts.getResultsCount());

        Mockito.verify(this.primitiveDataService).childrenOf(Mockito.any(PublicId.class));
        Mockito.verify(this.primitiveDataService, times(2)).descriptionsOf(Mockito.any(List.class));
    }

    @Test
    void testGetTinkarDescendantConcepts() {

        Mockito.when(this.primitiveDataService.descendantsOf(Mockito.any(PublicId.class)))
                .thenReturn(List.of(
                        PublicIds.of(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")),
                        PublicIds.of(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"))));

        Mockito.when(this.primitiveDataService.descriptionsOf(Mockito.any(List.class)))
                .thenReturn(List.of("TEST-DEP-DESCRIPTION"));

        TinkarGetResponse descendantConcepts =
                openCDXTinkarService.getTinkarDescendantConcepts(TinkarGetRequest.newBuilder()
                        .setConceptId("23e07078-f1e2-3f6a-9b7a-9397bcd91cfe")
                        .build());
        assertNotNull(descendantConcepts);
        Assertions.assertEquals(2, descendantConcepts.getResultsCount());

        Mockito.verify(this.primitiveDataService).descendantsOf(Mockito.any(PublicId.class));
    }

    @Test
    void testInit() {
        OpenCDXTinkarServiceImpl openCDXTinkarService = mock(OpenCDXTinkarServiceImpl.class);
        openCDXTinkarService.initialize();
        Mockito.verify(openCDXTinkarService, Mockito.times(1)).initialize();
    }

    @Test
    void testinitializePrimitiveData() {
        try (MockedStatic<PrimitiveData> primitiveData = Mockito.mockStatic(PrimitiveData.class);
                MockedStatic<CachingService> cachingService = Mockito.mockStatic(CachingService.class);
                MockedStatic<ServiceProperties> serviceProperties = Mockito.mockStatic(ServiceProperties.class)) {
            primitiveData.when(PrimitiveData::running).thenReturn(false);
        }
        // when(primitiveDataService.search("test", 10)).thenReturn(createSearchResults());

        OpenCDXTinkarServiceImpl openCDXTinkarService =
                new OpenCDXTinkarServiceImpl("parent", "child", primitiveDataService);
        assertNotNull(openCDXTinkarService);
    }

    private PrimitiveDataSearchResult[] createSearchResults() {
        PrimitiveDataSearchResult[] result = new PrimitiveDataSearchResult[1];
        result[0] = new PrimitiveDataSearchResult(
                -2144684618, -2147483638, -2147483638, 1, 13.158955F, "<B>Chronic</B> <B>disease</B>");
        return result;
    }
}
