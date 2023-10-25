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
package cdx.opencdx.media.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cdx.media.v2alpha.CreateMediaRequest;
import cdx.media.v2alpha.ListMediaRequest;
import cdx.media.v2alpha.MediaStatus;
import cdx.media.v2alpha.UpdateMediaRequest;
import cdx.opencdx.media.dto.FileUploadResponse;
import cdx.opencdx.media.model.OpenCDXMediaModel;
import cdx.opencdx.media.repository.OpenCDXMediaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @MockBean
    OpenCDXMediaRepository openCDXMediaRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.when(this.openCDXMediaRepository.save(Mockito.any(OpenCDXMediaModel.class)))
                .thenAnswer(new Answer<OpenCDXMediaModel>() {
                    @Override
                    public OpenCDXMediaModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXMediaModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        Mockito.when(this.openCDXMediaRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMediaModel>>() {
                    @Override
                    public Optional<OpenCDXMediaModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(
                                OpenCDXMediaModel.builder().id(argument).build());
                    }
                });

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/")
                        .content(this.objectMapper.writeValueAsString(CreateMediaRequest.getDefaultInstance()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void getMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void updateMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(put("/").content(this.objectMapper.writeValueAsString(UpdateMediaRequest.newBuilder()
                                .setId(ObjectId.get().toHexString())
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void deleteMedia() throws Exception {
        MvcResult result = this.mockMvc
                .perform(delete("/" + ObjectId.get().toHexString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void listMedia() throws Exception {
        Mockito.when(this.openCDXMediaRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.EMPTY_LIST, PageRequest.of(1, 10), 1));

        MvcResult result = this.mockMvc
                .perform(post("/list")
                        .content(this.objectMapper.writeValueAsString(ListMediaRequest.newBuilder()
                                .setPageNumber(1)
                                .setPageSize(10)
                                .setSortAscending(true)
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void upload() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile(
                "file", "1234567890.json", "application/json", "{\"key1\": \"value1\"}".getBytes());

        this.mockMvc
                .perform(multipart("/upload/" + ObjectId.get().toHexString())
                        .file(jsonFile)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void download() throws Exception {
        Assertions.assertNotNull(new FileUploadResponse());
        Mockito.reset(this.openCDXMediaRepository);
        Mockito.when(this.openCDXMediaRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMediaModel>>() {
                    @Override
                    public Optional<OpenCDXMediaModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMediaModel.builder()
                                .id(argument)
                                .status(MediaStatus.MEDIA_STATUS_ACTIVE)
                                .mimeType("application/json")
                                .build());
                    }
                });

        String fileId = ObjectId.get().toHexString();
        MockMultipartFile jsonFile = new MockMultipartFile(
                "file", "1234567890.json", "application/json", "{\"key1\": \"value1\"}".getBytes());

        this.mockMvc
                .perform(multipart("/upload/" + fileId).file(jsonFile).characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        MvcResult result = this.mockMvc
                .perform(get("/download/" + fileId + ".json").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void downloadFail() throws Exception {
        Assertions.assertNotNull(new FileUploadResponse());
        Mockito.reset(this.openCDXMediaRepository);
        Mockito.when(this.openCDXMediaRepository.findById(Mockito.any(ObjectId.class)))
                .thenAnswer(new Answer<Optional<OpenCDXMediaModel>>() {
                    @Override
                    public Optional<OpenCDXMediaModel> answer(InvocationOnMock invocation) throws Throwable {
                        ObjectId argument = invocation.getArgument(0);
                        return Optional.of(OpenCDXMediaModel.builder()
                                .id(argument)
                                .status(MediaStatus.MEDIA_STATUS_DELETED)
                                .mimeType("application/json")
                                .build());
                    }
                });

        String fileId = ObjectId.get().toHexString();
        MockMultipartFile jsonFile = new MockMultipartFile(
                "file", "1234567890.json", "application/json", "{\"key1\": \"value1\"}".getBytes());

        this.mockMvc
                .perform(multipart("/upload/" + fileId).file(jsonFile).characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        MvcResult result = this.mockMvc
                .perform(get("/download/" + fileId + ".json").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(403))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    void uploadFail_1() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile(
                "file", "..1234567890.json", "application/json", "{\"key1\": \"value1\"}".getBytes());

        this.mockMvc
                .perform(multipart("/upload/" + ObjectId.get().toHexString())
                        .file(jsonFile)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(400));
    }

    @Test
    void uploadFail_2() throws Exception {
        MockMultipartFile jsonFile =
                new MockMultipartFile("file", null, "application/json", "{\"key1\": \"value1\"}".getBytes());

        this.mockMvc
                .perform(multipart("/upload/" + ObjectId.get().toHexString())
                        .file(jsonFile)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}
