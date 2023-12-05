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
package cdx.opencdx.predictor.service.impl;

import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.neural.predictor.PredictorOutput;
import cdx.opencdx.grpc.neural.predictor.PredictorRequest;
import cdx.opencdx.grpc.neural.predictor.PredictorResponse;
import cdx.opencdx.predictor.service.PredictorService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for processing predictor Requests
 */
@Service
@Observed(name = "opencdx")
public class PredictorServiceImpl implements PredictorService {

    /**
     * Constructor taking the a PersonRepository
     *
     * @param personRepository    repository for interacting with the database.
     * @param openCDXAuditService Audit service for tracking FDA requirements
     * @param openCDXCurrentUser Current User Service.
     */
    @Autowired
    public PredictorServiceImpl(OpenCDXAuditService openCDXAuditService, OpenCDXCurrentUser openCDXCurrentUser) {}

    /**
     * Process the PredictData
     * @param request request the process
     * @return Message generated for this request.
     */
    @Override
    public PredictorResponse predict(PredictorRequest request) {
        // placeholder
        return PredictorResponse.newBuilder()
                .setPredictorOutput(
                        PredictorOutput.newBuilder().setPredictedValue("PredictorServiceImpl - PredictorResponse"))
                .build();
    }
}
