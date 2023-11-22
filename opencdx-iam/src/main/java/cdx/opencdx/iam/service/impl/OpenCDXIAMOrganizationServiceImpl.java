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
package cdx.opencdx.iam.service.impl;

import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.service.OpenCDXIAMOrganizationService;

public class OpenCDXIAMOrganizationServiceImpl implements OpenCDXIAMOrganizationService {
    /**
     * Method to create a new organization.
     *
     * @param request CreateOrganizationRequest for new organization.
     * @return CreateOrganizationResponse with the new organization created.
     */
    @Override
    public CreateOrganizationResponse createOrganization(CreateOrganizationRequest request) {
        return null;
    }

    /**
     * Method to get a specific organization.
     *
     * @param request GetOrganizationDetailsByIdRequest for organization to get.
     * @return GetOrganizationDetailsByIdResponse with the organization.
     */
    @Override
    public GetOrganizationDetailsByIdResponse getOrganizationDetailsById(GetOrganizationDetailsByIdRequest request) {
        return null;
    }

    /**
     * Method to update an organization.
     *
     * @param request UpdateOrganizationRequest for an organization to be updated.
     * @return UpdateOrganizationResponse with the updated organization.
     */
    @Override
    public UpdateOrganizationResponse updateOrganization(UpdateOrganizationRequest request) {
        return null;
    }

    /**
     * Method to get the list of organization.
     *
     * @return ListOrganizationsResponse with all the organization.
     */
    @Override
    public ListOrganizationsResponse listOrganizations() {
        return null;
    }
}
