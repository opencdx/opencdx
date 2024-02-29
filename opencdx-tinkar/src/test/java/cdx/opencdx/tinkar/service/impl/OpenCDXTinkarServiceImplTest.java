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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.grpc.tinkar.TinkarGetRequest;
import cdx.opencdx.grpc.tinkar.TinkarQueryRequest;
import cdx.opencdx.grpc.tinkar.TinkarQueryResponse;
import cdx.opencdx.grpc.tinkar.TinkarQueryResult;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import dev.ikm.tinkar.common.service.*;
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
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXTinkarServiceImplTest {

    OpenCDXTinkarService openCDXTinkarService;

    @Mock
    PrimitiveDataService primitiveDataService;

    @BeforeEach
    void setUp() {
        openCDXTinkarService = new OpenCDXTinkarServiceImpl("parent", "child");
    }

    @Test
    void testSearch() throws Exception {
        try (MockedStatic<PrimitiveData> primitiveData = Mockito.mockStatic(PrimitiveData.class);
                MockedStatic<CachingService> cachingService = Mockito.mockStatic(CachingService.class);
                MockedStatic<ServiceProperties> serviceProperties = Mockito.mockStatic(ServiceProperties.class)) {
            primitiveData.when(PrimitiveData::running).thenReturn(false);
            primitiveData.when(PrimitiveData::get).thenReturn(primitiveDataService);
            when(primitiveDataService.search("test", 10)).thenReturn(createResults());

            TinkarQueryResponse response = openCDXTinkarService.search(TinkarQueryRequest.newBuilder()
                    .setQuery("test")
                    .setMaxResults(10)
                    .build());

            log.info("**************");
            log.info(String.valueOf(response));

            Assertions.assertNotNull(response);
            Assertions.assertEquals(1, response.getResultsCount());

            primitiveData.verify(PrimitiveData::running);
            primitiveData.verify(PrimitiveData::get);
            verify(primitiveDataService).search("test", 10);
        }
    }

    @Test
    void testSearchException() throws Exception {
        try (MockedStatic<PrimitiveData> primitiveData = Mockito.mockStatic(PrimitiveData.class);
                MockedStatic<CachingService> cachingService = Mockito.mockStatic(CachingService.class);
                MockedStatic<ServiceProperties> serviceProperties = Mockito.mockStatic(ServiceProperties.class)) {
            primitiveData.when(PrimitiveData::running).thenReturn(false);
            primitiveData.when(PrimitiveData::get).thenReturn(primitiveDataService);
            when(primitiveDataService.search("test", 10)).thenThrow(Exception.class);

            try {
                openCDXTinkarService.search(TinkarQueryRequest.newBuilder()
                        .setQuery("test")
                        .setMaxResults(10)
                        .build());
            } catch (Exception e) {
                Assertions.assertEquals(OpenCDXBadRequest.class, e.getClass());
                Assertions.assertEquals("Search Failed", e.getMessage());
            }

            primitiveData.verify(PrimitiveData::running);
            primitiveData.verify(PrimitiveData::get);
            verify(primitiveDataService).search("test", 10);
        }
    }

    @Test
    void testGetEntity() throws Exception {
        try (MockedStatic<PrimitiveData> primitiveData = Mockito.mockStatic(PrimitiveData.class);
                MockedStatic<CachingService> cachingService = Mockito.mockStatic(CachingService.class);
                MockedStatic<ServiceProperties> serviceProperties = Mockito.mockStatic(ServiceProperties.class)) {
            primitiveData.when(PrimitiveData::running).thenReturn(false);
            primitiveData.when(PrimitiveData::get).thenReturn(primitiveDataService);
            when(primitiveDataService.search("nid=-2144684618", 1)).thenReturn(createResults());

            TinkarQueryResult result = openCDXTinkarService.getEntity(
                    TinkarGetRequest.newBuilder().setNid(-2144684618).build());

            Assertions.assertNotNull(result);
            Assertions.assertEquals(-2144684618, result.getNid());

            primitiveData.verify(PrimitiveData::running);
            primitiveData.verify(PrimitiveData::get);
            verify(primitiveDataService).search("nid=-2144684618", 1);
        }
    }

    @Test
    void testGetEntityNullResult() throws Exception {
        try (MockedStatic<PrimitiveData> primitiveData = Mockito.mockStatic(PrimitiveData.class);
                MockedStatic<CachingService> cachingService = Mockito.mockStatic(CachingService.class);
                MockedStatic<ServiceProperties> serviceProperties = Mockito.mockStatic(ServiceProperties.class)) {
            primitiveData.when(PrimitiveData::running).thenReturn(false);
            primitiveData.when(PrimitiveData::get).thenReturn(primitiveDataService);
            when(primitiveDataService.search("nid=-2144684618", 1)).thenReturn(new PrimitiveDataSearchResult[0]);

            TinkarQueryResult result = openCDXTinkarService.getEntity(
                    TinkarGetRequest.newBuilder().setNid(-2144684618).build());

            Assertions.assertNull(result);

            primitiveData.verify(PrimitiveData::running);
            primitiveData.verify(PrimitiveData::get);
            verify(primitiveDataService).search("nid=-2144684618", 1);
        }
    }

    @Test
    void testGetEntityException() throws Exception {
        try (MockedStatic<PrimitiveData> primitiveData = Mockito.mockStatic(PrimitiveData.class);
                MockedStatic<CachingService> cachingService = Mockito.mockStatic(CachingService.class);
                MockedStatic<ServiceProperties> serviceProperties = Mockito.mockStatic(ServiceProperties.class)) {
            primitiveData.when(PrimitiveData::running).thenReturn(false);
            primitiveData.when(PrimitiveData::get).thenReturn(primitiveDataService);
            when(primitiveDataService.search("nid=-2144684618", 1)).thenThrow(Exception.class);

            try {
                openCDXTinkarService.getEntity(
                        TinkarGetRequest.newBuilder().setNid(-2144684618).build());
            } catch (Exception e) {
                Assertions.assertEquals(OpenCDXBadRequest.class, e.getClass());
                Assertions.assertEquals("Entity Get Failed", e.getMessage());
            }

            primitiveData.verify(PrimitiveData::running);
            primitiveData.verify(PrimitiveData::get);
            verify(primitiveDataService).search("nid=-2144684618", 1);
        }
    }

    private PrimitiveDataSearchResult[] createResults() {
        PrimitiveDataSearchResult[] result = new PrimitiveDataSearchResult[1];
        result[0] = new PrimitiveDataSearchResult(
                -2144684618, -2147483638, -2147483638, 1, 13.158955F, "<B>Chronic</B> <B>disease</B>");
        return result;
    }
}
