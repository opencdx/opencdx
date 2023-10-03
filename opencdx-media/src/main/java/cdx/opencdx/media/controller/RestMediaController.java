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
package cdx.opencdx.media.controller;

import cdx.media.v2alpha.*;
import cdx.opencdx.media.dto.FileUploadResponse;
import cdx.opencdx.media.service.MediaService;
import cdx.opencdx.media.service.OpenCDXFileStorageService;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(
        value = "/media",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Observed(name = "opencdx")
public class RestMediaController {

    private final MediaService mediaService;
    private final OpenCDXFileStorageService openCDXFileStorageService;

    /**
     * Constructor that takes a MediaService
     */
    @Autowired
    public RestMediaController(MediaService mediaService, OpenCDXFileStorageService openCDXFileStorageService) {
        this.mediaService = mediaService;
        this.openCDXFileStorageService = openCDXFileStorageService;
    }

    /**
     * Create a Media.
     *
     * @param request the CreateMediaRequest
     * @return the created CreateMediaResponse
     */
    @PostMapping
    public ResponseEntity<CreateMediaResponse> createMedia(@RequestBody CreateMediaRequest request) {
        return new ResponseEntity<>(this.mediaService.createMedia(request), HttpStatus.OK);
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
                this.mediaService.getMedia(
                        GetMediaRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * Update the Media
     *
     * @param request the UpdateMediaRequest for updating the Media
     * @return the UpdateMediaResponse
     */
    @PutMapping
    public ResponseEntity<UpdateMediaResponse> updateMedia(@RequestBody UpdateMediaRequest request) {
        return new ResponseEntity<>(this.mediaService.updateMedia(request), HttpStatus.OK);
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
                this.mediaService.deleteMedia(
                        DeleteMediaRequest.newBuilder().setId(id).build()),
                HttpStatus.OK);
    }

    /**
     * List Media
     *
     * @param request Request for Media
     * @return the requested Media.
     */
    @PostMapping("/list")
    public ResponseEntity<ListMediaResponse> listNotificationEvents(@RequestBody ListMediaRequest request) {
        return new ResponseEntity<>(this.mediaService.listMedia(request), HttpStatus.OK);
    }

    /**
     * Method to upload files for storage.
     * @param file Multipart file as RequestParam "file"
     * @return FileUploadResponse indicating if successful.
     */
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok()
                .body(FileUploadResponse.builder()
                        .success(openCDXFileStorageService.storeFile(file))
                        .build());
    }
}
