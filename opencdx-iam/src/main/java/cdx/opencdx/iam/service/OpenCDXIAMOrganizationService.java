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
package cdx.opencdx.iam.service;

import cdx.opencdx.grpc.organization.*;

/**
 * Interface for the OpenCDXIAMOrganizationService
 */
public interface OpenCDXIAMOrganizationService {

    /**
     * Method to create a new organization.
     * @param request CreateOrganizationRequest for new organization.
     * @return CreateOrganizationResponse with the new organization created.
     */
    CreateOrganizationResponse createOrganization(CreateOrganizationRequest request);

    /**
     * Method to get a specific organization.
     * @param request GetOrganizationDetailsByIdRequest for organization to get.
     * @return GetOrganizationDetailsByIdResponse with the organization.
     */
    GetOrganizationDetailsByIdResponse getOrganizationDetailsById(GetOrganizationDetailsByIdRequest request);

    /**
     * Method to update an organization.
     * @param request UpdateOrganizationRequest for an organization to be updated.
     * @return UpdateOrganizationResponse with the updated organization.
     */
    UpdateOrganizationResponse updateOrganization(UpdateOrganizationRequest request);

    /**
     * Method to get the list of organization.
     * @return ListOrganizationsResponse with all the organization.
     */
    ListOrganizationsResponse listOrganizations();
}
