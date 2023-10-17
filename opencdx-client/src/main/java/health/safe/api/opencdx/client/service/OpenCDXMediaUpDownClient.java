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
package health.safe.api.opencdx.client.service;

import health.safe.api.opencdx.client.dto.FileUploadResponse;
import health.safe.api.opencdx.client.exceptions.OpenCDXClientException;
import org.springframework.core.io.Resource;

public interface OpenCDXMediaUpDownClient {

    FileUploadResponse upload(String file, String fileId) throws OpenCDXClientException;

    Resource download(String fileId, String ext) throws OpenCDXClientException;
}
