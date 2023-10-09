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

import cdx.media.v2alpha.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.protobuf.Timestamp;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class MediaPreProcessorTest {
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new ProtobufModule());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testPreprocessMediaRequest() throws JsonProcessingException {
        PreprocessMediaRequest preprocessMediaRequest =
                PreprocessMediaRequest.newBuilder().setCheck(true).build();
        log.info("PreprocessMediaRequest: {}", this.mapper.writeValueAsString(preprocessMediaRequest));
    }

    @Test
    void testPreprocessMediaResponse() throws JsonProcessingException {
        PreprocessMediaResponse preprocessMediaResponse = PreprocessMediaResponse.newBuilder()
                .setMediaPreprocessor(MediaPreprocessor.newBuilder()
                        .setId("id")
                        .setCreatedAt(Timestamp.newBuilder().setSeconds(1696732104))
                        .setCreator("creator")
                        .setUpdatedAt(Timestamp.newBuilder().setSeconds(1696733104))
                        .setModifier("modifier"))
                .build();
        log.info("PreprocessMediaRequest: {}", this.mapper.writeValueAsString(preprocessMediaResponse));
    }
}
