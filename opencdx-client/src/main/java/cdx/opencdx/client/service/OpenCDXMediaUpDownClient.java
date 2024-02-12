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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.FileUploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for the Media Uploading and Downloading client
 */
@FeignClient("media")
public interface OpenCDXMediaUpDownClient {

    /**
     * Method to upload files to Media
     * @param authorizationToken Token for authorization
     * @param file path to file to upload
     * @param fileId ID for upload file.
     * @return FileUploadResponse
     */
    @PostMapping(value = "/upload/{fileId}", consumes = "application/json")
    ResponseEntity<FileUploadResponse> uploadFile(
            @RequestHeader("Authorization") String authorizationToken,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @PathVariable String fileId);

    /**
     * Method to download files from Media
     * @param authorizationToken Token for authorization
     * @param fileId ID of file to download
     * @param ext Extension of file
     * @return downloaded Resource
     */
    @GetMapping(value = "/download/{fileId}.{ext}")
    ResponseEntity<Resource> download(
            @RequestHeader("Authorization") String authorizationToken,
            @PathVariable String fileId,
            @PathVariable String ext);
}
