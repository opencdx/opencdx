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
package proto;

import cdx.opencdx.grpc.service.tinkar.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class TinkarQueryTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testTinkarSearchQueryRequest() throws JsonProcessingException {
        TinkarSearchQueryRequest tinkarRequest = TinkarSearchQueryRequest.newBuilder()
                .setQuery("chronic disease")
                .setMaxResults(10)
                .build();
        Assertions.assertEquals(
                "{\"query\":\"chronic disease\",\"maxResults\":10}", this.mapper.writeValueAsString(tinkarRequest));
    }

    @Test
    void testTinkarSearchQueryResponse() throws JsonProcessingException {
        TinkarGetResult tinkarGetResult = TinkarGetResult.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .setDescription("TEST")
                .build();

        Assertions.assertEquals(
                "{\"conceptId\":\"550e8400-e29b-41d4-a716-446655440000\",\"description\":\"TEST\",\"name\":\"\"}",
                this.mapper.writeValueAsString(tinkarGetResult));

        TinkarSearchQueryResponse tinkarSearchQueryResponse = TinkarSearchQueryResponse.newBuilder()
                .addResults(tinkarGetResult)
                .build();

        Assertions.assertEquals(
                "{\"results\":[{\"conceptId\":\"550e8400-e29b-41d4-a716-446655440000\",\"description\":\"TEST\",\"name\":\"\"}]}",
                this.mapper.writeValueAsString(tinkarSearchQueryResponse));
    }

    @Test
    void testTinkarGetRequest() throws JsonProcessingException {
        TinkarGetRequest request = TinkarGetRequest.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .build();
        Assertions.assertEquals(
                "{\"conceptId\":\"550e8400-e29b-41d4-a716-446655440000\"}", this.mapper.writeValueAsString(request));
    }

    @Test
    void testTinkarGetResponse() throws JsonProcessingException {
        TinkarGetResult result = TinkarGetResult.newBuilder()
                .setConceptId("550e8400-e29b-41d4-a716-446655440000")
                .setDescription("TEST")
                .build();

        Assertions.assertEquals(
                "{\"conceptId\":\"550e8400-e29b-41d4-a716-446655440000\",\"description\":\"TEST\",\"name\":\"\"}",
                this.mapper.writeValueAsString(result));

        TinkarGetResponse tinkarGetResponse =
                TinkarGetResponse.newBuilder().addResults(result).build();

        Assertions.assertEquals(
                "{\"results\":[{\"conceptId\":\"550e8400-e29b-41d4-a716-446655440000\",\"description\":\"TEST\",\"name\":\"\"}]}",
                this.mapper.writeValueAsString(tinkarGetResponse));
    }
}
