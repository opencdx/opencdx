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
package cdx.opencdx.classification.service.impl;

import cdx.opencdx.classification.model.OpenCDXClassificationModel;
import cdx.opencdx.classification.service.OpenCDXClassifyProcessorService;
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import cdx.opencdx.commons.exceptions.OpenCDXDataLoss;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.media.Media;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import io.micrometer.observation.annotation.Observed;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service implementation for processing Classification Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
@SuppressWarnings({"java:S1068", "java:S125", "java:S1172"})
public class OpenCDXClassifyProcessorServiceImpl implements OpenCDXClassifyProcessorService {
    private final OpenCDXMediaUpDownClient openCDXMediaUpDownClient;

    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Constructor for OpenCDXClassifyProcessorServiceImpl
     * @param openCDXMediaUpDownClient service for media upload and download client
     * @param openCDXCurrentUser service for current user
     */
    public OpenCDXClassifyProcessorServiceImpl(
            OpenCDXMediaUpDownClient openCDXMediaUpDownClient, OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXMediaUpDownClient = openCDXMediaUpDownClient;
        this.openCDXCurrentUser = openCDXCurrentUser;
    }

    @Override
    @SuppressWarnings("java:S2119")
    public void classify(OpenCDXClassificationModel model) {
        log.trace("Executing classify operation.");
        Resource file = retrieveFile(model.getMedia());
        if (file != null) {
            log.trace("fileName: {}", file.getFilename());
        }
        Resource connectedFile = retrieveFile(model.getTestDetailsMedia());
        if (connectedFile != null) {
            log.trace("fileName: {}", connectedFile.getFilename());
        }
        ClassificationResponse.Builder builder = ClassificationResponse.newBuilder();
        builder.setMessage("Executed classify operation.");

        builder.setConfidence(new Random().nextFloat());
        builder.setPositiveProbability(new Random().nextFloat());
        builder.setAvailability(new Random().nextFloat() < 0.5 ? "Not Available" : "Available");
        builder.setCost(new Random().nextFloat(1000.00f));
        builder.setFurtherActions(
                new Random().nextFloat() < 0.5
                        ? "Take plenty of fluids, and Tylenol as needed for fever."
                        : "Grandma's Chicken Soup");
        builder.setFurtherActions(
                new Random().nextFloat() < 0.5 ? "Follow up with your physician." : "Hospitalization is required.");
        builder.setUserId(model.getUserAnswer().getUserId());

        model.setClassificationResponse(builder.build());
    }

    private Resource retrieveFile(Media model) {
        if (model != null) {
            log.trace("Retrieving file for classification.");

            try {
                MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
                MimeType type = allTypes.forName(model.getMimeType());
                String primaryExtension = type.getExtension().replace(".", "");
                log.trace("Downloading media for classification: {} as {}", model.getId(), primaryExtension);
                ResponseEntity<Resource> downloaded = this.openCDXMediaUpDownClient.download(
                        "Bearer " + this.openCDXCurrentUser.getCurrentUserAccessToken(),
                        model.getId(),
                        primaryExtension);
                return downloaded.getBody();
            } catch (OpenCDXInternal e) {
                log.error("Failed to download media for classification: {}", model.getId(), e);
                throw e;
            } catch (MimeTypeException e) {
                throw new OpenCDXDataLoss(
                        "OpenCDXClassifyProcessorServiceImpl",
                        1,
                        "Failed to identify extension: " + model.getMimeType(),
                        e);
            }
        }

        return null;
    }
}
