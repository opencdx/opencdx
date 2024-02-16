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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles({"test", "managed"})
@ExtendWith(MockitoExtension.class)
class OpenCDXRestTinkarSearchControllerTest {

    @Mock
    OpenCDXTinkarService openCDXTinkarService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new OpenCDXRestTinkarSearchController(openCDXTinkarService))
                .build();
    }

    @Test
    void search() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/").content("{\"name\": \"jeff\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("query", "chronic disease of respiratory")
                        .param("maxResults", "10"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void getEntity() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/nid")
                        .content("{\"name\": \"jeff\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("nid", "1"))
                .andExpect(status().is(200))
                .andReturn();
    }
}
