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

import cdx.opencdx.grpc.tinkar.*;
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
    void testTinkarQueryRequest() throws JsonProcessingException {
        TinkarQueryRequest tinkarRequest = TinkarQueryRequest.newBuilder()
                .setQuery("chronic disease")
                .setMaxResults(10)
                .build();
        Assertions.assertEquals(
                "{\"query\":\"chronic disease\",\"maxresults\":10}", this.mapper.writeValueAsString(tinkarRequest));
    }

    @Test
    void testTinkarQueryResponse() throws JsonProcessingException {
        TinkarQueryResult result = TinkarQueryResult.newBuilder()
                .setNid(-2144684618)
                .setRcNid(-2147393046)
                .setPatternNid(-2147483638)
                .setFieldIndex(1)
                .setScore(13.158955f)
                .setHighlightedString("<B>Chronic</B> <B>disease</B> <B>of</B> <B>respiratory</B> <B>system</B>")
                .build();

        TinkarQueryResponse tinkarReply =
                TinkarQueryResponse.newBuilder().addResults(result).build();

        Assertions.assertEquals(
                "{\"results\":[{\"nid\":-2144684618,\"rcNid\":-2147393046,\"patternNid\":-2147483638,\"fieldIndex\":1,\"score\":13.158955,\"highlightedString\":\"<B>Chronic</B> <B>disease</B> <B>of</B> <B>respiratory</B> <B>system</B>\"}]}",
                this.mapper.writeValueAsString(tinkarReply));
    }
}
