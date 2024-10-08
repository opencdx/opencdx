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
package cdx.opencdx.iam.service;

import cdx.opencdx.grpc.service.iam.*;

/**
 * Interface for the OpenCDXAnalysisEngineService
 */
public interface OpenCDXAnalysisEngineService {
    /**
     * Method to create AnalysisEngine
     * @param request CreateAnalysisEngineRequest for measurement.
     * @return CreateAnalysisEngineResponse with measurement.
     */
    CreateAnalysisEngineResponse createAnalysisEngine(CreateAnalysisEngineRequest request);

    /**
     * Method to get AnalysisEngine
     * @param request GetAnalysisEngineResponse for measurement.
     * @return GetAnalysisEngineRequest with measurement.
     */
    GetAnalysisEngineResponse getAnalysisEngine(GetAnalysisEngineRequest request);

    /**
     * Method to update AnalysisEngine.
     * @param request UpdateAnalysisEngineRequest for measurement.
     * @return UpdateAnalysisEngineResponse with measurement.
     */
    UpdateAnalysisEngineResponse updateAnalysisEngine(UpdateAnalysisEngineRequest request);

    /**
     * Method to delete AnalysisEngine
     * @param request DeleteAnalysisEngineRequest for measurement.
     * @return DeleteAnalysisEngineResponse with measurement.
     */
    DeleteAnalysisEngineResponse deleteAnalysisEngine(DeleteAnalysisEngineRequest request);

    /**
     * Method to list AnalysisEngines
     * @param request ListAnalysisEnginesRequest for measurement.
     * @return ListAnalysisEnginesResponse with measurement.
     */
    ListAnalysisEnginesResponse listAnalysisEngines(ListAnalysisEnginesRequest request);
}
