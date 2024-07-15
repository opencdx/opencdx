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
import cdx.opencdx.communications.service.OpenCDXSMSService;
import io.micrometer.observation.annotation.Observed;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for Sending SMS Notifications.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXSMSServiceImpl implements OpenCDXSMSService {

    /**
     * Default Constructor
     */
    public OpenCDXSMSServiceImpl() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }
    /**
     * Method to send SMS Notificaiton
     *
     * @param message Content of the SMS notification
     * @param phoneNumbers List of phone numbers to send the notification to.
     * @return boolean indicating if successful.
     */
    @Override
    public boolean sendSMS(String message, List<String> phoneNumbers) {

        /*
        If we do not have any phone numbers to send, then return true that we have completed sending
        SMS notifications.
         */
        if (ListUtils.isEmpty(phoneNumbers)) {
            return true;
        }
        log.info("Message has been sent. PhoneNumbers: {} \nMessage:\n {}", phoneNumbers, message);
        return true;
    }
}
