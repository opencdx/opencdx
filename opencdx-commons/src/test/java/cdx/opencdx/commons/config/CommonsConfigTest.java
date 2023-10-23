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
package cdx.opencdx.commons.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;

import cdx.open_audit.v2alpha.Actor;
import cdx.open_audit.v2alpha.AuditEvent;
import cdx.open_audit.v2alpha.AuditEventType;
import cdx.opencdx.commons.handlers.OpenCDXGrpcExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(classes = OpenCDXGrpcExceptionHandler.class)
@ExtendWith(SpringExtension.class)
class CommonsConfigTest {

    CommonsConfig commonsConfig;

    @BeforeEach
    void setUp() {
        this.commonsConfig = new CommonsConfig();
    }

    @AfterEach
    void tearDown() {}

    @Test
    void objectMapper() {
        Assertions.assertNotNull(this.commonsConfig.objectMapper());
    }

    @Test
    void auditEventPrint() throws JsonProcessingException {
        ObjectMapper mapper = this.commonsConfig.objectMapper();
        log.info("*********************************");
        log.info(mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(AuditEvent.newBuilder()
                        .setEventType(AuditEventType.AUDIT_EVENT_TYPE_USER_PHI_CREATED)
                        .setCreated(Timestamp.newBuilder().build())
                        .setActor(Actor.newBuilder()
                                .setNetworkAddress("192.198.0.1")
                                .build())));
        log.info("*********************************");
    }

    @Test
    void nullCheck() {
        Assertions.assertNotNull(this.commonsConfig);
    }

    @Test
    void testMongoTemplate() {
        mockConstruction(MongoTemplate.class);
        MongoDatabaseFactory factory = mock(MongoDatabaseFactory.class);
        MongoConverter converter = mock(MongoConverter.class);
        Object mt = commonsConfig.mongoTemplate(factory, converter);
        Assertions.assertNotNull(mt);
    }
}
