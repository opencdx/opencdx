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
package cdx.opencdx.media.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface to implement File Storage Solutions.
 */
public interface OpenCDXFileStorageService {
    /**
     * Store a file in the current storage service.
     * @param file MultipartFile uploaded to the system
     * @param fileId String identifier for file.
     * @return String indicating the filename.
     */
    boolean storeFile(MultipartFile file, String fileId);

    /**
     * Generates response with the file to return.
     * @param fileId ID of the file
     * @param extension extension of the file.
     * @return Response Entity with the file.
     */
    ResponseEntity<Resource> readFile(String fileId, String extension);
}
