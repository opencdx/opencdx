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
package cdx.opencdx.connected.test.service;

import cdx.opencdx.grpc.inventory.DeleteResponse;
import cdx.opencdx.grpc.inventory.TestCase;
import cdx.opencdx.grpc.inventory.TestCaseIdRequest;

/**
 * TestCase service
 */
public interface OpenCDXTestCaseService {
    /**
     * Method to get a test case
     * @param request ID of test case to retrieve
     * @return The TestCase requested.
     */
    TestCase getTestCaseById(TestCaseIdRequest request);

    /**
     * Method to add a test case
     * @param request TestCase data to add
     * @return TestCase with id.
     */
    TestCase addTestCase(TestCase request);

    /**
     * Method to update a TestCase
     * @param request Updated Testcase data
     * @return the updated TestCase
     */
    TestCase updateTestCase(TestCase request);

    /**
     * Method to delete a TestCase
     * @param request ID of TestCase to delete
     * @return Response indicating if successful.
     */
    DeleteResponse deleteTestCase(TestCaseIdRequest request);
}
