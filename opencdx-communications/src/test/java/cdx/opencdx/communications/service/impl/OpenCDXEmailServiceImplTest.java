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
package cdx.opencdx.communications.service.impl;

import cdx.opencdx.grpc.data.Attachment;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles({"test", "managed"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.cloud.config.enabled=false", "mongock.enabled=false"})
class OpenCDXEmailServiceImplTest {

    @Autowired
    OpenCDXEmailService openCDXEmailService;

    @Test
    void sendEmail() {

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(Attachment.newBuilder().setFilename("test1.txt").build());
        attachments.add(Attachment.newBuilder().setFilename("test2.txt").build());
        attachments.add(Attachment.newBuilder().setFilename("test1.png").build());
        attachments.add(Attachment.newBuilder().setFilename("test2.png").build());

        Assertions.assertTrue(() -> this.openCDXEmailService.sendEmail(
                "Email Subject",
                "Sample Email content text.",
                List.of("test1@opencdx.com", "test2@opencdx.com", "list3@opencdx.com"),
                List.of("test4@opencdx.com", "test5@opencdx.com", "list6@opencdx.com"),
                List.of("test7@opencdx.com", "test8@opencdx.com", "list9@opencdx.com"),
                attachments));
    }
}
