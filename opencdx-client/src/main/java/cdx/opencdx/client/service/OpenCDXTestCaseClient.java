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
package cdx.opencdx.client.service;

import cdx.opencdx.client.dto.OpenCDXCallCredentials;
import cdx.opencdx.client.exceptions.OpenCDXClientException;
import cdx.opencdx.grpc.inventory.*;

/**
 * Interface for communicating with the Logistics TestCase microservice.
 */
public interface OpenCDXTestCaseClient {
    /**
     * Method to gRPC Call TestCase Service getTestCaseById() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    TestCase getTestCaseById(TestCaseIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call TestCase Service addTestCase() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    TestCase addTestCase(TestCase request, OpenCDXCallCredentials openCDXCallCredentials) throws OpenCDXClientException;

    /**
     * Method to gRPC Call TestCase Service updateTestCase() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    TestCase updateTestCase(TestCase request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call TestCase Service deleteTestCase() api.
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    DeleteResponse deleteTestCase(TestCaseIdRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to gRPC Call TestCase Service listTestCase() api.
     *
     * @param request Client Rules request
     * @param openCDXCallCredentials Call Credentials to use for send.
     * @return Message response.
     */
    TestCaseListResponse listTestCase(TestCaseListRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
