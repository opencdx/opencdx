package cdx.opencdx.classification.service.impl;

import cdx.opencdx.classification.model.OpenCDXClassificationModel;
import cdx.opencdx.classification.service.OpenCDXClassifyProcessorService;
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service implementation for processing Classification Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
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
        if(model.getMedia() != null) {
            ResponseEntity<Resource> downloaded = this.openCDXMediaUpDownClient.download(model.getMedia().getId(), "tmp");

            try {
                long numBytes = downloaded.getBody().contentLength();
                log.info("File Size: {} bytes", numBytes);
                log.info("File Name: {}", downloaded.getBody().getFilename());
            } catch (IOException e) {
                log.error("Error when trying to log file size and name", e);
            }
        }
    }
}