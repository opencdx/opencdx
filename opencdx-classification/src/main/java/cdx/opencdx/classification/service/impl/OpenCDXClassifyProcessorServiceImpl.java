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
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * Constructor for OpenCDXClassifyProcessorServiceImpl
     * @param openCDXMediaUpDownClient service for media upload &  download client
     */
    public OpenCDXClassifyProcessorServiceImpl(OpenCDXMediaUpDownClient openCDXMediaUpDownClient) {
        this.openCDXMediaUpDownClient = openCDXMediaUpDownClient;
    }

    @Override
    public void classify(OpenCDXClassificationModel model) {
        Resource file = retrieveFile(model);
        if (file != null) {
            log.info("fileName: {}", file.getFilename());
        }
        model.setClassificationResponse(ClassificationResponse.newBuilder()
                .setMessage("Executed classify operation.")
                .build());
    }

    private Resource retrieveFile(OpenCDXClassificationModel model) {
                if (model.getMedia() != null) {
                    log.info(
                            "Downloading media for classification: {}", model.getMedia().getId());
                    try {
                        ResponseEntity<Resource> downloaded =
                                this.openCDXMediaUpDownClient.download(model.getMedia().getId(), "tmp");
                        return downloaded.getBody();
                    } catch (OpenCDXInternal e) {
                        log.error(
                                "Failed to download media for classification: {}",
                                model.getMedia().getId(),
                                e);
                        throw e;
                    }
                }

        return null;
    }
}
