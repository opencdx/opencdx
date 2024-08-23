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

import cdx.opencdx.grpc.service.tinkar.TinkarSearchQueryRequest;
import cdx.opencdx.grpc.service.tinkar.TinkarSearchQueryResponse;
import cdx.opencdx.grpc.service.tinkar.TinkarSearchQueryResult;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXRestTinkarSearchControllerTest {

    @MockBean
    OpenCDXTinkarService openCDXTinkarService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {

        TinkarSearchQueryRequest request = TinkarSearchQueryRequest.newBuilder()
                .setQuery("chronic disease of respiratory system")
                .setMaxResults(10)
                .build();
        TinkarSearchQueryResponse response = TinkarSearchQueryResponse.newBuilder()
                .addResults(createResult())
                .build();

        when(openCDXTinkarService.search(request)).thenReturn(response);

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testSearch() throws Exception {

        MvcResult result = this.mockMvc
                .perform(get("/search")
                        .param("query", "chronic disease of respiratory")
                        .param("maxResults", "10"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void testGetEntity() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/conceptId").param("conceptId", "550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void testGetTinkarChildConcepts() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/children/conceptId").param("conceptId", "550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void testGetTinkarDescendantConcepts() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/descendants/conceptId").param("conceptId", "550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void testGetLIDRRecordConceptsFromTestKit() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/lidr-records/testKitConceptId")
                        .param("testKitConceptId", "550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void testGetResultConformanceConceptsFromLIDRRecord() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/result-conformances/lidrRecordConceptId")
                        .param("lidrRecordConceptId", "550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void testGetAllowedResultConceptsFromResultConformanced() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/allowed-results/resultConformanceConceptId")
                        .param("resultConformanceConceptId", "550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().is(200))
                .andReturn();
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
