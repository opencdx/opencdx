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
package health.safe.api.opencdx.connected.test.service;

import cdx.open_connected_test.v2alpha.ConnectedTest;
import cdx.open_connected_test.v2alpha.TestIdRequest;
import cdx.open_connected_test.v2alpha.TestSubmissionResponse;

/**
 * Interface for the OpenCDXConnectedTestService
 */
public interface OpenCDXConnectedTestService {

    /**
     * Submits Test results to be stored for a user.
     * @param connectedTest Connected Test results to store
     * @return ID of the connected Test Results.
     */
    public TestSubmissionResponse submitTest(ConnectedTest connectedTest);

    /**
     * Get the connected test results for a specific test.
     * @param testIdRequest ID of the test to retrieve.
     * @return The connected test results.
     */
    public ConnectedTest getTestDetailsById(TestIdRequest testIdRequest);
}