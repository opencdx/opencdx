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

import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.connected.*;

/**
 * Interface for the Connected Tests client
 */
public interface OpenCDXConnectedTestClient {

    /**
     * Method to submit a ConnectedTest for processing.
     *
     * @param connectedTest ConnectedTest submitted
     * @return ID for the connectedTest
     * @throws OpenCDXClientException Exception in the client.
     */
    TestSubmissionResponse submitTest(ConnectedTest connectedTest) throws OpenCDXClientException;

    /**
     * Method to get a ConnectedTest
     *
     * @param id id of the ConnectedTest to retrieve.
     * @return The requested ConnectedTest.
     * @throws OpenCDXClientException Exception in the client.
     */
    ConnectedTest getTestDetailsById(String id) throws OpenCDXClientException;

    /**
     * List Connected tests
     *
     * @param connectedTestListRequest request for Connected Tests.
     * @return the requested connected tests.
     * @throws OpenCDXClientException Exception in the client.
     */
    ConnectedTestListResponse listConnectedTests(ConnectedTestListRequest connectedTestListRequest)
            throws OpenCDXClientException;

    /**
     * List Connected tests by national health id
     *
     * @param connectedTestListByNHIDRequest request for Connected Tests.
     * @return the requested connected tests
     * @throws OpenCDXClientException Exception in the client.
     */
    ConnectedTestListByNHIDResponse listConnectedTestsByNHID(
            ConnectedTestListByNHIDRequest connectedTestListByNHIDRequest) throws OpenCDXClientException;
}
