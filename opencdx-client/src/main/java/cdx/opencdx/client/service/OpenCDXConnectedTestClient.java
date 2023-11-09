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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.grpc.connected.*;

/**
 * Client Interface to the gRPC Connected Tests service
 */
public interface OpenCDXConnectedTestClient {

    /**
     * Method to submit a ConnectedTest for processing.
     *
     * @param connectedTest ConnectedTest submitted
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return TestSubmissionResponse for the connectedTest
     */
    TestSubmissionResponse submitTest(ConnectedTest connectedTest, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * Method to get a ConnectedTest
     *
     * @param testIdRequest id of the ConnectedTest to retrieve.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return The requested ConnectedTest.
     */
    ConnectedTest getTestDetailsById(TestIdRequest testIdRequest, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * List Connected tests
     *
     * @param connectedTestListRequest request for Connected Tests.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the requested connected tests.
     */
    ConnectedTestListResponse listConnectedTests(
            ConnectedTestListRequest connectedTestListRequest, OpenCDXCallCredentials openCDXCallCredentials);

    /**
     * List Connected tests by national health id
     *
     * @param connectedTestListByNHIDRequest request for Connected Tests.
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return the requested connected tests
     */
    ConnectedTestListByNHIDResponse listConnectedTestsByNHID(
            ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest,
            OpenCDXCallCredentials openCDXCallCredentials);
}
