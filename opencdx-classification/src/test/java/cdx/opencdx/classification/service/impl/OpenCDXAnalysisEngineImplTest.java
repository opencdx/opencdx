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
import cdx.opencdx.client.service.OpenCDXMediaUpDownClient;
import cdx.opencdx.client.service.OpenCDXQuestionnaireClient;
import cdx.opencdx.client.service.OpenCDXTestCaseClient;
import cdx.opencdx.commons.exceptions.OpenCDXInternal;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.media.Media;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OpenCDXAnalysisEngineImplTest {

    OpenCDXMediaUpDownClient openCDXMediaUpDownClient;
    OpenCDXCurrentUser openCDXCurrentUser;

    OpenCDXQuestionnaireClient openCDXQuestionnaireClient;

    OpenCDXTestCaseClient openCDXTestCaseClient;

    @Test
    void classifyExceptionOpenCDXInternal() {
        OpenCDXAnalysisEngineImpl processorService = new OpenCDXAnalysisEngineImpl(
                openCDXMediaUpDownClient, openCDXCurrentUser, openCDXQuestionnaireClient, openCDXTestCaseClient);
        OpenCDXClassificationModel model = Mockito.mock(OpenCDXClassificationModel.class);
        Media media = Mockito.mock(Media.class);
        Mockito.when(model.getMedia()).thenReturn(media);
        Mockito.when(media.getMimeType()).thenThrow(OpenCDXInternal.class);
        Assertions.assertThrows(OpenCDXInternal.class, () -> processorService.analyzeQuestionnaire(model));
    }
}
