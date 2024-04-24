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
package cdx.opencdx.health.service.impl;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cdx.opencdx.health.dto.openfda.OpenFDA;
import cdx.opencdx.health.dto.openfda.Product;
import cdx.opencdx.health.dto.openfda.Result;
import cdx.opencdx.health.dto.openfda.Search;
import cdx.opencdx.health.feign.OpenCDXOpenFDAClient;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class OpenCDXApiFDAImplTest {

    @Test
    void test() {
        OpenCDXOpenFDAClient openCDXOpenFDAClient = mock(OpenCDXOpenFDAClient.class);
        ResponseEntity<Search> drugs = mock(ResponseEntity.class);
        Search search = mock(Search.class);
        when(drugs.getBody()).thenReturn(search);
        Result result = mock(Result.class);
        OpenFDA openFDA = mock(OpenFDA.class);
        when(result.getOpenfda()).thenReturn(openFDA);
        when(openFDA.getBrand_name()).thenReturn(Collections.singletonList("brandName"));
        when(openFDA.getProduct_ndc()).thenReturn(Collections.singletonList("productNdc"));
        when(search.getResults()).thenReturn(Collections.singletonList(result));
        // when(openCDXOpenFDAClient.getDrugs("products.brand_name: openFDAApiKey", 1000,0)).thenReturn(drugs);
        when(search.getResults()).thenReturn(List.of(result));
        when(openCDXOpenFDAClient.getDrugs(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(drugs);

        ResponseEntity<Search> label = mock(ResponseEntity.class);
        Search searchLabel = mock(Search.class);
        when(label.getBody()).thenReturn(searchLabel);
        Result resultLabel = mock(Result.class);
        when(searchLabel.getResults()).thenReturn(Collections.EMPTY_LIST);
        when(openCDXOpenFDAClient.getLabel(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(label);

        OpenCDXApiFDAImpl openCDXApiFDA = new OpenCDXApiFDAImpl(openCDXOpenFDAClient, "openFDAApiKey");
        Assertions.assertDoesNotThrow(() -> openCDXApiFDA.getMedicationsByBrandName("openFDAApiKey"));
        // when(openCDXApiFDA.getMedicationsByBrandName("brandNamePrefix")).thenReturn(Collections.emptyList());
    }

    @Test
    void test2() {
        OpenCDXOpenFDAClient openCDXOpenFDAClient = mock(OpenCDXOpenFDAClient.class);
        ResponseEntity<Search> drugs = mock(ResponseEntity.class);
        Search search = mock(Search.class);
        when(drugs.getBody()).thenReturn(search);
        Result result = mock(Result.class);
        OpenFDA openFDA = mock(OpenFDA.class);
        when(result.getOpenfda()).thenReturn(openFDA);
        when(openFDA.getBrand_name()).thenReturn(Collections.singletonList("brandName"));
        when(openFDA.getProduct_ndc()).thenReturn(Collections.singletonList("productNdc"));
        when(search.getResults()).thenReturn(Collections.singletonList(result));
        // when(openCDXOpenFDAClient.getDrugs("products.brand_name: openFDAApiKey", 1000,0)).thenReturn(drugs);
        when(search.getResults()).thenReturn(List.of(result));
        when(openCDXOpenFDAClient.getDrugs(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(drugs);

        ResponseEntity<Search> label = mock(ResponseEntity.class);
        Search searchLabel = mock(Search.class);
        when(label.getBody()).thenReturn(searchLabel);
        Result resultLabel = mock(Result.class);
        when(searchLabel.getResults()).thenReturn(null);
        when(openCDXOpenFDAClient.getLabel(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(label);

        OpenCDXApiFDAImpl openCDXApiFDA = new OpenCDXApiFDAImpl(openCDXOpenFDAClient, "openFDAApiKey");
        Assertions.assertDoesNotThrow(() -> openCDXApiFDA.getMedicationsByBrandName("openFDAApiKey"));
        // when(openCDXApiFDA.getMedicationsByBrandName("brandNamePrefix")).thenReturn(Collections.emptyList());
    }

    @Test
    void test3() {
        OpenCDXOpenFDAClient openCDXOpenFDAClient = mock(OpenCDXOpenFDAClient.class);
        ResponseEntity<Search> drugs = mock(ResponseEntity.class);
        Search search = mock(Search.class);
        when(drugs.getBody()).thenReturn(search);
        Result result = mock(Result.class);
        OpenFDA openFDA = mock(OpenFDA.class);
        when(result.getOpenfda()).thenReturn(openFDA);
        when(openFDA.getBrand_name()).thenReturn(Collections.singletonList("brandName"));
        when(openFDA.getProduct_ndc()).thenReturn(Collections.singletonList("productNdc"));
        when(search.getResults()).thenReturn(Collections.singletonList(result));
        // when(openCDXOpenFDAClient.getDrugs("products.brand_name: openFDAApiKey", 1000,0)).thenReturn(drugs);
        when(search.getResults()).thenReturn(List.of(result));
        when(openCDXOpenFDAClient.getDrugs(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(drugs);

        ResponseEntity<Search> label = mock(ResponseEntity.class);
        Search searchLabel = mock(Search.class);
        when(label.getBody()).thenReturn(null);
        when(openCDXOpenFDAClient.getLabel(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(label);

        OpenCDXApiFDAImpl openCDXApiFDA = new OpenCDXApiFDAImpl(openCDXOpenFDAClient, "openFDAApiKey");
        Assertions.assertDoesNotThrow(() -> openCDXApiFDA.getMedicationsByBrandName("openFDAApiKey"));
        // when(openCDXApiFDA.getMedicationsByBrandName("brandNamePrefix")).thenReturn(Collections.emptyList());
    }

    @Test
    void test4() {
        OpenCDXOpenFDAClient openCDXOpenFDAClient = mock(OpenCDXOpenFDAClient.class);
        ResponseEntity<Search> drugs = mock(ResponseEntity.class);
        Search search = mock(Search.class);
        when(drugs.getBody()).thenReturn(search);
        Result result = mock(Result.class);
        OpenFDA openFDA = mock(OpenFDA.class);
        when(result.getOpenfda()).thenReturn(openFDA);
        when(openFDA.getBrand_name()).thenReturn(Collections.singletonList("brandName"));
        when(openFDA.getProduct_ndc()).thenReturn(Collections.singletonList("productNdc"));
        when(search.getResults()).thenReturn(Collections.singletonList(result));
        // when(openCDXOpenFDAClient.getDrugs("products.brand_name: openFDAApiKey", 1000,0)).thenReturn(drugs);
        when(search.getResults()).thenReturn(List.of(result));
        when(openCDXOpenFDAClient.getDrugs(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(drugs);

        ResponseEntity<Search> label = mock(ResponseEntity.class);
        Search searchLabel = mock(Search.class);
        when(label.getBody()).thenReturn(searchLabel);
        Result resultLabel = mock(Result.class);
        when(resultLabel.getOpenfda()).thenReturn(openFDA);
        Product product = mock(Product.class);
        when(resultLabel.getProducts()).thenReturn(List.of(product));
        when(searchLabel.getResults()).thenReturn(Collections.singletonList(resultLabel));
        when(openCDXOpenFDAClient.getLabel(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(label);

        OpenCDXApiFDAImpl openCDXApiFDA = new OpenCDXApiFDAImpl(openCDXOpenFDAClient, "openFDAApiKey");
        Assertions.assertDoesNotThrow(() -> openCDXApiFDA.getMedicationsByBrandName("openFDAApiKey"));
        // when(openCDXApiFDA.getMedicationsByBrandName("brandNamePrefix")).thenReturn(Collections.emptyList());
    }
}
// 5037510569085
// 1770-825
