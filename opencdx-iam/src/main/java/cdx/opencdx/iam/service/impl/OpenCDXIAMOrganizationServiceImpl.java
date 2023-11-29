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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.organization.*;
import cdx.opencdx.iam.model.OpenCDXIAMOrganizationModel;
import cdx.opencdx.iam.repository.OpenCDXIAMOrganizationRepository;
import cdx.opencdx.iam.service.OpenCDXIAMOrganizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXIAMOrganizationServiceImpl implements OpenCDXIAMOrganizationService {

    private static final String DOMAIN = "OpenCDXIAMOrganizationServiceImpl";

    private final OpenCDXIAMOrganizationRepository openCDXIAMOrganizationRepository;

    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;

    public OpenCDXIAMOrganizationServiceImpl(
            OpenCDXIAMOrganizationRepository openCDXIAMOrganizationRepository,
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper) {
        this.openCDXIAMOrganizationRepository = openCDXIAMOrganizationRepository;
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
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

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Create Organization",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    "Create Organization_" + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, "Failed to convert OpenCDXIAMOrganizationModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", request.toString());
            throw openCDXNotAcceptable;
        }

        return CreateOrganizationResponse.newBuilder()
                .setOrganization(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to get a specific organization.
     *
     * @param request GetOrganizationDetailsByIdRequest for organization to get.
     * @return GetOrganizationDetailsByIdResponse with the organization.
     */
    @Override
    public GetOrganizationDetailsByIdResponse getOrganizationDetailsById(GetOrganizationDetailsByIdRequest request) {
        OpenCDXIAMOrganizationModel model = this.openCDXIAMOrganizationRepository
                .findById(new ObjectId(request.getOrganizationId()))
                .orElseThrow(() ->
                        new OpenCDXNotFound(DOMAIN, 3, "FAILED_TO_FIND_ORGANIZATION" + request.getOrganizationId()));
        return GetOrganizationDetailsByIdResponse.newBuilder()
                .setOrganization(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to update an organization.
     *
     * @param request UpdateOrganizationRequest for an organization to be updated.
     * @return UpdateOrganizationResponse with the updated organization.
     */
    @Override
    public UpdateOrganizationResponse updateOrganization(UpdateOrganizationRequest request) {

        if (!this.openCDXIAMOrganizationRepository.existsById(
                new ObjectId(request.getOrganization().getId()))) {
            throw new OpenCDXNotFound(
                    DOMAIN,
                    3,
                    "FAILED_TO_FIND_ORGANIZATION" + request.getOrganization().getId());
        }
        OpenCDXIAMOrganizationModel model =
                this.openCDXIAMOrganizationRepository.save(new OpenCDXIAMOrganizationModel(request.getOrganization()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Update Organization",
                    SensitivityLevel.SENSITIVITY_LEVEL_LOW,
                    "Update Organization_" + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, "Failed to convert OpenCDXIAMOrganizationModel", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put("Object", request.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateOrganizationResponse.newBuilder()
                .setOrganization(model.getProtobufMessage())
                .build();
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
                .addAllOrganizations(all.stream()
                        .map(OpenCDXIAMOrganizationModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
