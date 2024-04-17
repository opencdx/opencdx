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

import cdx.opencdx.commons.collections.ListUtils;
import cdx.opencdx.communications.service.OpenCDXEmailService;
import cdx.opencdx.grpc.data.Attachment;
import io.micrometer.observation.annotation.Observed;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Log based implementation of OpenCDXEmailService.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXEmailServiceImpl implements OpenCDXEmailService {

    /**
     * Default Constructor
     */
    @Autowired
    public OpenCDXEmailServiceImpl() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public boolean sendEmail(
            String subject,
            String content,
            List<String> toEmails,
            List<String> ccEmails,
            List<String> bccEmails,
            List<Attachment> attachments) {

        if (ListUtils.isEmpty(toEmails) && ListUtils.isEmpty(ccEmails) && ListUtils.isEmpty(bccEmails)) {
            return false;
        }
        log.info(
                """

                         TO: {}
                         CC: {}
                        BCC: {}
                    Subject: {}
                Attachments: {}
                   Body:
                {}
                """,
                String.join(", ", ListUtils.safe(toEmails)),
                String.join(", ", ListUtils.safe(ccEmails)),
                String.join(", ", ListUtils.safe(bccEmails)),
                subject,
                ListUtils.safe(attachments).stream()
                        .map(Attachment::getFilename)
                        .collect(Collectors.joining(", ")),
                content);
        return true;
    }
}
