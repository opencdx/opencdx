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

import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.model.OpenCDXIAMOrganizationModel;
import cdx.opencdx.iam.repository.OpenCDXIAMOrganizationRepository;
import cdx.opencdx.iam.service.OpenCDXIAMOrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OpenCDXIAMOrganizationServiceImpl implements OpenCDXIAMOrganizationService {

    private static final String DOMAIN = "OpenCDXIAMOrganizationServiceImpl";

    private final OpenCDXIAMOrganizationRepository openCDXIAMOrganizationRepository;

    public OpenCDXIAMOrganizationServiceImpl(
            OpenCDXIAMOrganizationRepository openCDXIAMOrganizationRepository) {
        this.openCDXIAMOrganizationRepository = openCDXIAMOrganizationRepository;
    }

    /**
     * Method to create a new organization.
     *
     * @param request CreateOrganizationRequest for new organization.
     * @return CreateOrganizationResponse with the new organization created.
     */
    @Override
    public CreateOrganizationResponse createOrganization(CreateOrganizationRequest request) {

        OpenCDXIAMOrganizationModel model = new OpenCDXIAMOrganizationModel(request.getOrganization());
        model = this.openCDXIAMOrganizationRepository.save(model);

        return CreateOrganizationResponse.newBuilder().setOrganization(model.getProtobufMessage()).build();
    }

    /**
     * Method to get a specific organization.
     *
     * @param request GetOrganizationDetailsByIdRequest for organization to get.
     * @return GetOrganizationDetailsByIdResponse with the organization.
     */
    @Override
    public GetOrganizationDetailsByIdResponse getOrganizationDetailsById(GetOrganizationDetailsByIdRequest request) {
        OpenCDXIAMOrganizationModel model = this.openCDXIAMOrganizationRepository.findById(
                new ObjectId(request.getOrganizationId())).orElseThrow(() -> new OpenCDXNotFound(
                DOMAIN, 3,
                "FAILED_TO_FIND_ORGANIZATION" + request.getOrganizationId()));
        return GetOrganizationDetailsByIdResponse.newBuilder().setOrganization(model.getProtobufMessage()).build();
    }

    /**
     * Method to update an organization.
     *
     * @param request UpdateOrganizationRequest for an organization to be updated.
     * @return UpdateOrganizationResponse with the updated organization.
     */
    @Override
    public UpdateOrganizationResponse updateOrganization(UpdateOrganizationRequest request) {

        OpenCDXIAMOrganizationModel model = this.openCDXIAMOrganizationRepository.findById(
                new ObjectId(request.getOrganization().getOrganizationId())).orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN, 3,
                "FAILED_TO_FIND_ORGANIZATION" + request.getOrganization().getOrganizationId()));
        model.setName(request.getOrganization().getName());
        model.setAddress(request.getOrganization().getAddress());
        model = this.openCDXIAMOrganizationRepository.save(model);
        return UpdateOrganizationResponse.newBuilder().setOrganization(model.getProtobufMessage()).build();
    }

    /**
     * Method to get the list of organization.
     *
     * @return ListOrganizationsResponse with all the organization.
     */
    @Override
    @SuppressWarnings("java:S6204")
    public ListOrganizationsResponse listOrganizations() {
        List<OpenCDXIAMOrganizationModel> all = this.openCDXIAMOrganizationRepository.findAll();
        return ListOrganizationsResponse.newBuilder()
                .addAllOrganizations(all.stream().map(OpenCDXIAMOrganizationModel::getProtobufMessage)
                        .collect(Collectors.toList())).build();
    }
}
