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
package cdx.opencdx.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

/**
 * indicates if the file upload was successful.
 * Copied from media module. Any changes to that class must be
 * reflected to this copy.
 */
@Data
@Builder
@AllArgsConstructor
@Generated
public class FileUploadResponse {
    /**
     * Default Constructor
     */
    public FileUploadResponse() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    private boolean success;
}
