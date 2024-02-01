package cdx.opencdx.classification.service;

import cdx.opencdx.classification.model.OpenCDXClassificationModel;

/**
 * Interface for the ClassificationProcessorService.  Performs classification of Questionnaire and Connected Test
 * data.
 */
public interface OpenCDXClassifyProcessorService {
    /**
     * Classify the OpenCDXClassificationModel
     * @param model model to classify
     */
    void classify(OpenCDXClassificationModel model);
}
