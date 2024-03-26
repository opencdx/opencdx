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
package cdx.opencdx.classification.service;

import cdx.opencdx.grpc.neural.classification.ClassificationRequest;
import cdx.opencdx.grpc.neural.classification.ClassificationResponse;
import cdx.opencdx.grpc.neural.classification.ClientRulesRequest;
import cdx.opencdx.grpc.neural.classification.RuleSetsResponse;

/**
 * Interface for the ClassificationService
 */
public interface OpenCDXClassificationService {
    /**
     * Process the ClassificationRequest
     * @param request request the process
     * @return Message generated for this request.
     */
    ClassificationResponse classify(ClassificationRequest request);

    /**
     * Operation to get rulesets
     * @param request the request to retrieve rules at the client level
     * @return Response containing a list of rulesets
     */
    RuleSetsResponse getRuleSets(ClientRulesRequest request);
}
