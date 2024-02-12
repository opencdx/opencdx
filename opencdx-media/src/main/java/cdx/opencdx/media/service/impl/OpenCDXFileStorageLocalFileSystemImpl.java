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
package cdx.opencdx.media.service.impl;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import cdx.opencdx.commons.exceptions.OpenCDXForbidden;
import cdx.opencdx.commons.exceptions.OpenCDXInternalServerError;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.grpc.media.*;
import cdx.opencdx.media.model.OpenCDXMediaModel;
import cdx.opencdx.media.repository.OpenCDXMediaRepository;
import cdx.opencdx.media.service.OpenCDXFileStorageService;
import io.micrometer.observation.annotation.Observed;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Local File System storage implementation.
 */
@Slf4j
@Service
@ExcludeFromJacocoGeneratedReport
@Observed(name = "opencdx")
public class OpenCDXFileStorageLocalFileSystemImpl implements OpenCDXFileStorageService {
    private static final String DOMAIN = "OpenCDXFileStorageLocalFileSystemImpl";
    private final Path fileStorageLocation;

    private final OpenCDXMediaRepository openCDXMediaRepository;

    /**
     * Constructor for creating a local file storage solution
     *
     * @param env                    Environment running within.
     * @param openCDXMediaRepository Repository for storing media records.
     */
    @Autowired
    public OpenCDXFileStorageLocalFileSystemImpl(Environment env, OpenCDXMediaRepository openCDXMediaRepository) {
        this.fileStorageLocation = Paths.get(env.getProperty("media.upload-dir", "./uploads"))
                .toAbsolutePath()
                .normalize();
        this.openCDXMediaRepository = openCDXMediaRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new OpenCDXInternalServerError(DOMAIN, 1, "Could not create media upload directory.", ex);
        }
    }

    @Override
    public boolean storeFile(MultipartFile file, String fileId) {
        OpenCDXMediaModel media = this.openCDXMediaRepository
                .findById(new ObjectId(fileId))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 4, "Failed to find media: " + fileId));

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains("..")) {
            throw new OpenCDXFailedPrecondition(
                    DOMAIN, 2, "Filename contains invalid path operator " + file.getOriginalFilename());
        }

        String fileName = fileId + "." + getFileExtension(file.getOriginalFilename());

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            log.info("Storing file: {}", targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            media.setSize(file.getSize());
            media.setUpdated(Instant.now());
            media.setMimeType(file.getContentType());
            media.setEndpoint("/media/download/" + fileName);
            media.setStatus(MediaStatus.MEDIA_STATUS_ACTIVE);
            log.info("Download Endpoint: {}", media.getEndpoint());
            this.openCDXMediaRepository.save(media);
            return true;
        } catch (IOException ex) {
            throw new OpenCDXInternalServerError(DOMAIN, 3, "Failed to store: " + fileName, ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    public ResponseEntity<Resource> readFile(String fileId, String extension) {

        log.info("Reading file: {}", fileId + "." + extension);
        OpenCDXMediaModel media = this.openCDXMediaRepository
                .findById(new ObjectId(fileId))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 4, "Failed to find media: " + fileId));

        String fileName = fileId + "." + extension;
        File file = this.fileStorageLocation.resolve(fileName).toFile();

        if (!media.getStatus().equals(MediaStatus.MEDIA_STATUS_ACTIVE)) {
            throw new OpenCDXForbidden(DOMAIN, 6, "Resource Not Available: " + fileId);
        }

        try {
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(media.getMimeType()))
                    .body(new InputStreamResource(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            throw new OpenCDXNotFound(DOMAIN, 5, "Failed to find: " + file.getAbsoluteFile(), e);
        }
    }
}
