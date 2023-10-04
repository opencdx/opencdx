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
package health.safe.api.opencdx.media.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.media.v2alpha.CreateMediaRequest;
import cdx.media.v2alpha.ListMediaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class RestMediaControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/media")
                        .content(this.objectMapper.writeValueAsString(CreateMediaRequest.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"uploadUrl\":\"\"}", content);
    }

    @Test
    void getMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/media/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("{}", content);
    }

    @Test
    void updateMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/media")
                        .content(this.objectMapper.writeValueAsString(CreateMediaRequest.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("{}", content);
    }

    @Test
    void deleteMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/media/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("{}", content);
    }

    @Test
    void listMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/media/list")
                        .content(this.objectMapper.writeValueAsString(ListMediaRequest.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals(
                "{\"pageSize\":0,\"pageNumber\":0,\"sortAscending\":false,\"pageCount\":0,\"templates\":[]}", content);
    }

    @Test
    void upload() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile(
                "file", "1234567890.json", "application/json", "{\"key1\": \"value1\"}".getBytes());

        this.mockMvc
                .perform(multipart("/media/upload/" + ObjectId.get().toHexString()).file(jsonFile).characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void uploadFail_1() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile(
                "file", "..1234567890.json", "application/json", "{\"key1\": \"value1\"}".getBytes());

        this.mockMvc
                .perform(multipart("/media/upload/" + ObjectId.get().toHexString()).file(jsonFile).characterEncoding("UTF-8"))
                .andExpect(status().is(400));
    }

    @Test
    void uploadFail_2() throws Exception {
        MockMultipartFile jsonFile =
                new MockMultipartFile("file", null, "application/json", "{\"key1\": \"value1\"}".getBytes());

        this.mockMvc
                .perform(multipart("/media/upload/" + ObjectId.get().toHexString()).file(jsonFile).characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}
