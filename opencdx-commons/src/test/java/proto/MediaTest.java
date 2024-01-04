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
package proto; /*
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

import cdx.opencdx.grpc.media.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class MediaTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreateMediaRequest() throws JsonProcessingException {
        CreateMediaRequest createMediaRequest = CreateMediaRequest.newBuilder()
                .setType(MediaType.MEDIA_TYPE_IMAGE)
                .setName("name")
                .setShortDescription("shortDescription")
                .setDescription("description")
                .addAllLabels(List.of("labels1", "labels2"))
                .build();
        log.info("CreateMediaRequest: {}", this.mapper.writeValueAsString(createMediaRequest));
    }

    @Test
    void testCreateMediaResponse() throws JsonProcessingException {
        CreateMediaResponse createMediaResponse = CreateMediaResponse.newBuilder()
                .setMedia(Media.newBuilder()
                        .setId("id")
                        .setCreatedAt(Timestamp.newBuilder().setSeconds(1696732104))
                        .setUpdatedAt(Timestamp.newBuilder().setSeconds(1696932104))
                        .setOrganizationId(ObjectId.get().toHexString())
                        .setWorkspaceId(ObjectId.get().toHexString())
                        .setName("name")
                        .setShortDescription("shortDescription")
                        .setDescription("description")
                        .setType(MediaType.MEDIA_TYPE_IMAGE)
                        .addAllLabels(List.of("labels1"))
                        .setMimeType("mimeType")
                        .setSize(2)
                        .setLocation("location")
                        .setEndpoint("endpoint")
                        .setStatus(MediaStatus.MEDIA_STATUS_ACTIVE)
                        .build())
                .setUploadUrl("uploadUrl")
                .build();
        log.info("CreateMediaResponse: {}", this.mapper.writeValueAsString(createMediaResponse));
    }

    @Test
    void testGetMediaRequest() throws JsonProcessingException {
        GetMediaRequest mediaRequest = GetMediaRequest.newBuilder().setId("id").build();
        log.info("GetMediaRequest: {}", this.mapper.writeValueAsString(mediaRequest));
    }

    @Test
    void testGetMediaResponse() throws JsonProcessingException {
        GetMediaResponse mediaResponse = GetMediaResponse.newBuilder()
                .setMedia(Media.newBuilder()
                        .setId("id")
                        .setCreatedAt(Timestamp.newBuilder().setSeconds(1696732104))
                        .setUpdatedAt(Timestamp.newBuilder().setSeconds(1696932104))
                        .setOrganizationId(ObjectId.get().toHexString())
                        .setWorkspaceId(ObjectId.get().toHexString())
                        .setName("name")
                        .setShortDescription("shortDescription")
                        .setDescription("description")
                        .setType(MediaType.MEDIA_TYPE_IMAGE)
                        .addAllLabels(List.of("labels1"))
                        .setMimeType("mimeType")
                        .setSize(2)
                        .setLocation("location")
                        .setEndpoint("endpoint")
                        .setStatus(MediaStatus.MEDIA_STATUS_ACTIVE)
                        .build())
                .build();
        log.info("GetMediaResponse: {}", this.mapper.writeValueAsString(mediaResponse));
    }

    @Test
    void testListMediaRequest() throws JsonProcessingException {
        ListMediaRequest listMediaRequest = ListMediaRequest.newBuilder()
                .setPageSize(1)
                .setPageNumber(2)
                .setSortAscending(true)
                .build();
        log.info("ListMediaRequest: {}", this.mapper.writeValueAsString(listMediaRequest));
    }

    @Test
    void testListMediaResponse() throws JsonProcessingException {
        ListMediaResponse listMediaResponse = ListMediaResponse.newBuilder()
                .setPageSize(1)
                .setPageNumber(2)
                .setSortAscending(true)
                .setPageCount(2)
                .addAllTemplates(List.of(Media.newBuilder()
                        .setId("id")
                        .setCreatedAt(Timestamp.newBuilder().setSeconds(1696732104))
                        .setUpdatedAt(Timestamp.newBuilder().setSeconds(1696932104))
                        .setOrganizationId(ObjectId.get().toHexString())
                        .setWorkspaceId(ObjectId.get().toHexString())
                        .setName("name")
                        .setShortDescription("shortDescription")
                        .setDescription("description")
                        .setType(MediaType.MEDIA_TYPE_IMAGE)
                        .addAllLabels(List.of("labels1"))
                        .setMimeType("mimeType")
                        .setSize(2)
                        .setLocation("location")
                        .setEndpoint("endpoint")
                        .setStatus(MediaStatus.MEDIA_STATUS_ACTIVE)
                        .build()))
                .build();
        log.info("ListMediaResponse: {}", this.mapper.writeValueAsString(listMediaResponse));
    }

    @Test
    void testMediaFilter() throws JsonProcessingException {
        MediaFilter mediaFilter = MediaFilter.newBuilder().setFilter("filter").build();
        log.info("MediaFilter: {}", this.mapper.writeValueAsString(mediaFilter));
    }

    @Test
    void testUpdateMediaRequest() throws JsonProcessingException {
        UpdateMediaRequest updateMediaRequest = UpdateMediaRequest.newBuilder()
                .setId("id")
                .setName("name")
                .setShortDescription("shortDescription")
                .setDescription("description")
                .addAllLabels(List.of("labels"))
                .setType(MediaType.MEDIA_TYPE_IMAGE)
                .build();
        log.info("UpdateMediaRequest: {}", this.mapper.writeValueAsString(updateMediaRequest));
    }
}
