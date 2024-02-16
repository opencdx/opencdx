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
package cdx.opencdx.media.controller;

import cdx.opencdx.grpc.media.*;
import cdx.opencdx.media.dto.FileUploadResponse;
import cdx.opencdx.media.service.OpenCDXFileStorageService;
import cdx.opencdx.media.service.OpenCDXMediaService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for the /media api's
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class OpenCDXRestMediaController {

    private final OpenCDXMediaService openCDXMediaService;
    private final OpenCDXFileStorageService openCDXFileStorageService;

    /**
     * Constructore for the Rest Media Controller
     * @param openCDXMediaService Service for processing requests.
     * @param openCDXFileStorageService File storage service for storing files.
     */
    @Autowired
    public OpenCDXRestMediaController(
            OpenCDXMediaService openCDXMediaService, OpenCDXFileStorageService openCDXFileStorageService) {
        this.openCDXMediaService = openCDXMediaService;
        this.openCDXFileStorageService = openCDXFileStorageService;
    }

    /**
     * Create a Media.
     *
     * @param request the CreateMediaRequest
     * @return the created CreateMediaResponse
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateMediaResponse> createMedia(@RequestBody CreateMediaRequest request) {
        return new ResponseEntity<>(this.openCDXMediaService.createMedia(request), HttpStatus.OK);
    }

    /**
     * Gets a Media
     *
     * @param id the Media ID to retrieve
     * @return the GetMediaResponse for your media.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetMediaResponse> getMedia(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXMediaService.getMedia(
                        GetMediaRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update the Media
     *
     * @param request the UpdateMediaRequest for updating the Media
     * @return the UpdateMediaResponse
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateMediaResponse> updateMedia(@RequestBody UpdateMediaRequest request) {
        return new ResponseEntity<>(this.openCDXMediaService.updateMedia(request), HttpStatus.OK);
    }

    /**
     * Delete the Media with the id.
     *
     * @param id the id of the Media to delete
     * @return a DeleteMediaResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteMediaResponse> deleteMedia(@PathVariable String id) {
        return new ResponseEntity<>(
                this.openCDXMediaService.deleteMedia(
                        DeleteMediaRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List Media
     *
     * @param request Request for Media
     * @return the requested Media.
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListMediaResponse> listMedia(@RequestBody ListMediaRequest request) {
        return new ResponseEntity<>(this.openCDXMediaService.listMedia(request), HttpStatus.OK);
    }

    /**
     * Method to upload files for storage.
     * @param file Multipart file as RequestParam "file"
     * @param fileId File ID for the Media file.
     * @return FileUploadResponse indicating if successful.
     */
    @PostMapping(value = "/upload/{fileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file, @PathVariable String fileId) {
        return ResponseEntity.ok()
                .body(FileUploadResponse.builder()
                        .success(openCDXFileStorageService.storeFile(file, fileId))
                        .build());
    }

    /**
     * Method to download the files.
     * @param fileId ID of the file to download
     * @param ext File extension
     * @return the File.
     */
    @GetMapping(value = "/download/{fileId}.{ext}")
    public ResponseEntity<Resource> download(@PathVariable String fileId, @PathVariable String ext) {
        log.info("Downloading file: {}", fileId + "." + ext);
        return this.openCDXFileStorageService.readFile(fileId, ext);
    }
}
