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
package cdx.opencdx.connected.lab.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXServiceUnavailable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.connected.lab.config.OpenCDXLabConnectionFactoryBean;
import cdx.opencdx.connected.lab.handler.OpenCDXLabConnected;
import cdx.opencdx.connected.lab.model.OpenCDXConnectedLabModel;
import cdx.opencdx.connected.lab.repository.OpenCDXConnectedLabRepository;
import cdx.opencdx.connected.lab.service.OpenCDXConnectedLabService;
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.common.Pagination;
import cdx.opencdx.grpc.lab.connected.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * OpenCDXConnectedLabServiceImpl is an implementation class for OpenCDXConnectedLabService interface.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
@SuppressWarnings("java:S1068")
public class OpenCDXConnectedLabServiceImpl implements OpenCDXConnectedLabService {
    private static final String CONNECTED_LAB = "CONNECTED-LAB";
    private static final String DOMAIN = "OpenCDXConnectedLabServiceImpl";
    private static final String FAILED_TO_CONVERT_CONNECTED_LAB = "Failed to Convert Connected Lab";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_FIND_CONNECTED_LAB = "Failed to find Connected Lab: ";
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXConnectedLabRepository openCDXConnectedLabRepository;
    private final OpenCDXLabConnectionFactoryBean openCDXLabConnectionFactoryBean;

    /**
     * Constructor that takes a OpenCDXCurrentUser, OpenCDXAuditService, ObjectMapper, OpenCDXConnectedLabRepository,
     * and OpenCDXLabConnectionFactoryBean.
     * @param openCDXCurrentUser the current user
     * @param openCDXAuditService the audit service
     * @param objectMapper the object mapper
     * @param openCDXConnectedLabRepository the connected lab repository
     * @param openCDXLabConnectionFactoryBean the lab connection factory
     */
    public OpenCDXConnectedLabServiceImpl(
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXConnectedLabRepository openCDXConnectedLabRepository,
            OpenCDXLabConnectionFactoryBean openCDXLabConnectionFactoryBean) {
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXConnectedLabRepository = openCDXConnectedLabRepository;
        this.openCDXLabConnectionFactoryBean = openCDXLabConnectionFactoryBean;
    }

    @Override
    public LabFindingsResponse submitLabFindings(LabFindings request) {
        log.info("Submitting lab findings");

        OpenCDXIdentifier organizationId =
                new OpenCDXIdentifier(request.getBasicInfo().getOrganizationId());
        OpenCDXIdentifier workspaceId =
                new OpenCDXIdentifier(request.getBasicInfo().getWorkspaceId());

        Optional<OpenCDXConnectedLabModel> openCDXConnectedLabModel =
                this.openCDXConnectedLabRepository.findByOrganizationIdAndWorkspaceId(organizationId, workspaceId);

        if (openCDXConnectedLabModel.isEmpty()) {
            openCDXConnectedLabModel = this.openCDXConnectedLabRepository.findByOrganizationId(organizationId);
        }

        if (openCDXConnectedLabModel.isEmpty()) {
            log.error("Failed to find connected lab for organizationId: " + organizationId + " and workspaceId: "
                    + workspaceId);
            throw new OpenCDXNotFound(
                    DOMAIN,
                    7,
                    "Failed to find connected lab for organizationId: " + organizationId + " and workspaceId: "
                            + workspaceId);
        }

        try {
            OpenCDXLabConnected connection = this.openCDXLabConnectionFactoryBean.getConnection(
                    openCDXConnectedLabModel.get().getIdentifier());

            return connection.submitLabFindings(openCDXConnectedLabModel.get(), request);

        } catch (Exception e) {
            log.error("Failed to load lab: " + openCDXConnectedLabModel.get().getIdentifier(), e);
            throw new OpenCDXServiceUnavailable(
                    DOMAIN,
                    8,
                    "Failed load lab: " + openCDXConnectedLabModel.get().getIdentifier(),
                    e);
        }
    }

    @Override
    public CreateConnectedLabResponse createConnectedLab(CreateConnectedLabRequest request) {
        log.info("Creating connected lab");
        OpenCDXConnectedLabModel model =
                this.openCDXConnectedLabRepository.save(new OpenCDXConnectedLabModel(request.getConnectedLab()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Creating Connected-Lab",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    CONNECTED_LAB + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, FAILED_TO_CONVERT_CONNECTED_LAB, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return CreateConnectedLabResponse.newBuilder()
                .setConnectedLab(model.getProtobuf())
                .build();
    }

    @Override
    public GetConnectedLabResponse getConnectedLab(GetConnectedLabRequest request) {
        log.info("Getting connected lab");
        OpenCDXConnectedLabModel model = this.openCDXConnectedLabRepository
                .findById(new OpenCDXIdentifier(request.getConnectedLabId()))
                .orElseThrow(() ->
                        new OpenCDXNotFound(DOMAIN, 4, FAILED_TO_FIND_CONNECTED_LAB + request.getConnectedLabId()));
        return GetConnectedLabResponse.newBuilder()
                .setConnectedLab(model.getProtobuf())
                .build();
    }

    @Override
    public UpdateConnectedLabResponse updateConnectedLab(UpdateConnectedLabRequest request) {
        if (!this.openCDXConnectedLabRepository.existsById(
                new OpenCDXIdentifier(request.getConnectedLab().getId()))) {
            throw new OpenCDXNotFound(
                    DOMAIN,
                    2,
                    FAILED_TO_FIND_CONNECTED_LAB + request.getConnectedLab().getId());
        }

        OpenCDXConnectedLabModel model =
                this.openCDXConnectedLabRepository.save(new OpenCDXConnectedLabModel(request.getConnectedLab()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Updating Connected-Lab",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    CONNECTED_LAB + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_CONNECTED_LAB, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateConnectedLabResponse.newBuilder()
                .setConnectedLab(model.getProtobuf())
                .build();
    }

    @Override
    public DeleteConnectedLabResponse deleteConnectedLab(DeleteConnectedLabRequest request) {
        log.info("Deleting connected lab");

        OpenCDXConnectedLabModel model = this.openCDXConnectedLabRepository
                .findById(new OpenCDXIdentifier(request.getConnectedLabId()))
                .orElseThrow(() ->
                        new OpenCDXNotFound(DOMAIN, 5, FAILED_TO_FIND_CONNECTED_LAB + request.getConnectedLabId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Deleting Connected-Lab",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    CONNECTED_LAB + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_CONNECTED_LAB, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }

        this.openCDXConnectedLabRepository.deleteById(model.getId());
        return DeleteConnectedLabResponse.newBuilder()
                .setConnectedLab(model.getProtobuf())
                .build();
    }

    @Override
    public ListConnectedLabsResponse listConnectedLabs(ListConnectedLabsRequest request) {
        log.info("Listing connected labs");
        Pageable pageable;
        if (request.getPagination().hasSort()) {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize(),
                    request.getPagination().getSortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                    request.getPagination().getSort());
        } else {
            pageable = PageRequest.of(
                    request.getPagination().getPageNumber(),
                    request.getPagination().getPageSize());
        }

        Page<OpenCDXConnectedLabModel> all = this.openCDXConnectedLabRepository.findAll(pageable);

        return ListConnectedLabsResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllConnectedLabs(
                        all.map(OpenCDXConnectedLabModel::getProtobuf).toList())
                .build();
    }
}
