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
package health.safe.api.opencdx.media.service.impl;

import health.safe.api.opencdx.commons.exceptions.OpenCDXFailedPrecondition;
import health.safe.api.opencdx.commons.exceptions.OpenCDXInternalServerError;
import health.safe.api.opencdx.media.service.OpenCDXFileStorageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OpenCDXFileStorageLocalFileSystemImpl implements OpenCDXFileStorageService {
    private static final String DOMAIN = "OpenCDXFileStorageLocalFileSystemImpl";
    private final Path fileStorageLocation;

    @Autowired
    public OpenCDXFileStorageLocalFileSystemImpl(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("media.upload-dir", "./uploads/files"))
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new OpenCDXInternalServerError(DOMAIN, 1, "Could not create media upload directory.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    @Override
    public boolean storeFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains("..")) {
            throw new OpenCDXFailedPrecondition(
                    DOMAIN, 2, "Filename contains invalid path operator " + file.getOriginalFilename());
        }

        String fileName = new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return true;
        } catch (IOException ex) {
            throw new OpenCDXInternalServerError(DOMAIN, 3, "Failed to store: " + fileName, ex);
        }
    }
}
