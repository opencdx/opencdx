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
package cdx.opencdx.commons.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/** Status Message Class provides the DTO for various error messages that OpenCDX can throw */
@Data
@Builder
@AllArgsConstructor
public class StatusMessage {
    /**
     * Default Constructor
     */
    public StatusMessage() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    /** The error cause, if an alien or another application exception is caught and wrapped. */
    private Throwable cause;

    /** The time stamp when the exception was created. */
    @Builder.Default
    private Date timeStamp = new Date();

    /** The Rest URI path. */
    private String path;

    /** Error code that uniquely define the error .*/
    private String errorCode;

    /** Generic business message understandable to the consumers .*/
    private String businessMessage;

    /** Any additional parameters needed to construct a meaningful error description for the user. */
    private Map<String, String[]> parameters;

    /**
     * Contains a description of the error with all the necessary details needed for the application
     * operators, and possibly the application developers, to understand what error occurred.
     */
    private List<String> errorMessages;

    /**
     * Contains additional error data
     */
    @Builder.Default
    private Map<String, String> metadata = null;
}
