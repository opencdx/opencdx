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
package cdx.opencdx.health.service.impl;

import cdx.opencdx.commons.data.OpenCDXIdentifier;
import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.commons.service.OpenCDXDocumentValidator;
import cdx.opencdx.grpc.data.Pagination;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.model.OpenCDXAnalysisEngineModel;
import cdx.opencdx.health.repository.OpenCDXAnalysisEngineRepository;
import cdx.opencdx.health.service.OpenCDXAnalysisEngineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for the Temperature Measurement Service
 */
@Service
@Slf4j
@Observed(name = "opencdx")
public class OpenCDXAnalysisEngineServiceImpl implements OpenCDXAnalysisEngineService {

    private static final String DOMAIN = "OpenCDXAnalysisEngineServiceImpl";
    private static final String ANALYSIS_ENGINE = "ANALYSIS ENGINE: ";
    private static final String OBJECT = "OBJECT";
    private static final String FAILED_TO_CONVERT_ANALYSIS_ENGINE = "Failed to convert AnalysisEngine";
    private static final String FAILED_TO_FIND_ANALYSIS_ENGINE = "Failed to find AnalysisEngine ";
    private final OpenCDXAuditService openCDXAuditService;
    private final OpenCDXCurrentUser openCDXCurrentUser;
    private final ObjectMapper objectMapper;
    private final OpenCDXDocumentValidator openCDXDocumentValidator;
    private final OpenCDXAnalysisEngineRepository openCDXAnalysisEngineRepository;

    /**
     * Constructor for the Temperature Measurement Service Implementation
     * @param openCDXAuditService Audit Service
     * @param openCDXCurrentUser Current User Service
     * @param objectMapper Object Mapper
     * @param openCDXDocumentValidator Document Validator
     * @param openCDXAnalysisEngineRepository Temperature Measurement Repository
     */
    public OpenCDXAnalysisEngineServiceImpl(
            OpenCDXAuditService openCDXAuditService,
            OpenCDXCurrentUser openCDXCurrentUser,
            ObjectMapper objectMapper,
            OpenCDXDocumentValidator openCDXDocumentValidator,
            OpenCDXAnalysisEngineRepository openCDXAnalysisEngineRepository) {
        this.openCDXAuditService = openCDXAuditService;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.objectMapper = objectMapper;
        this.openCDXDocumentValidator = openCDXDocumentValidator;
        this.openCDXAnalysisEngineRepository = openCDXAnalysisEngineRepository;
    }

    @Override
    public CreateAnalysisEngineResponse createAnalysisEngine(CreateAnalysisEngineRequest request) {
        OpenCDXAnalysisEngineModel model =
                this.openCDXAnalysisEngineRepository.save(new OpenCDXAnalysisEngineModel(request.getAnalysisEngine()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Analysis Engine created",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ANALYSIS_ENGINE + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 1, FAILED_TO_CONVERT_ANALYSIS_ENGINE, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return CreateAnalysisEngineResponse.newBuilder()
                .setAnalysisEngine((model.getProtobufMessage()))
                .build();
    }

    @Override
    public GetAnalysisEngineResponse getAnalysisEngine(GetAnalysisEngineRequest request) {
        OpenCDXAnalysisEngineModel model = this.openCDXAnalysisEngineRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 1, FAILED_TO_FIND_ANALYSIS_ENGINE + request.getId()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "AnalysisEngine Accessed",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ANALYSIS_ENGINE + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_ANALYSIS_ENGINE, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return GetAnalysisEngineResponse.newBuilder()
                .setAnalysisEngine((model.getProtobufMessage()))
                .build();
    }

    @Override
    public UpdateAnalysisEngineResponse updateAnalysisEngine(UpdateAnalysisEngineRequest request) {
        OpenCDXAnalysisEngineModel model = this.openCDXAnalysisEngineRepository
                .findById(new OpenCDXIdentifier(request.getAnalysisEngine().getId()))
                .orElseThrow(() -> new OpenCDXNotFound(
                        DOMAIN,
                        1,
                        FAILED_TO_FIND_ANALYSIS_ENGINE
                                + request.getAnalysisEngine().getId()));

        model = this.openCDXAnalysisEngineRepository.save(model.update(request.getAnalysisEngine()));

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "AnalysisEngine Updated",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ANALYSIS_ENGINE + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, FAILED_TO_CONVERT_ANALYSIS_ENGINE, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return UpdateAnalysisEngineResponse.newBuilder()
                .setAnalysisEngine(model.getProtobufMessage())
                .build();
    }

    @Override
    public SuccessResponse deleteAnalysisEngine(DeleteAnalysisEngineRequest request) {
        OpenCDXAnalysisEngineModel model = this.openCDXAnalysisEngineRepository
                .findById(new OpenCDXIdentifier(request.getId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 2, FAILED_TO_FIND_ANALYSIS_ENGINE + request.getId()));

        this.openCDXAnalysisEngineRepository.deleteById(model.getId());
        log.info("Deleted Analysis Engine: {}", request.getId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.config(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Analaysis Engine Deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    ANALYSIS_ENGINE + model.getId(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_ANALYSIS_ENGINE, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
            throw openCDXNotAcceptable;
        }
        return SuccessResponse.newBuilder().setSuccess(true).build();
    }

    @Override
    public ListAnalysisEnginesResponse listAnalysisEngines(ListAnalysisEnginesRequest request) {
        log.trace("Searching Database for Analysis Engines");
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
        Page<OpenCDXAnalysisEngineModel> all = Page.empty();

        all = this.openCDXAnalysisEngineRepository.findAllByOrganizationId(request.getOrganizationId(), pageable);

        log.trace("Found Analysis Engine in database");

        all.get().forEach(model -> {
            try {
                OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
                this.openCDXAuditService.config(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        "Analysis Engine accessed",
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        ANALYSIS_ENGINE + model.getId(),
                        this.objectMapper.writeValueAsString(model.getProtobufMessage()));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 5, FAILED_TO_CONVERT_ANALYSIS_ENGINE, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, model.toString());
                throw openCDXNotAcceptable;
            }
        });

        return ListAnalysisEnginesResponse.newBuilder()
                .setPagination(Pagination.newBuilder(request.getPagination())
                        .setTotalPages(all.getTotalPages())
                        .setTotalRecords(all.getTotalElements())
                        .build())
                .addAllAnalysisEngine(all.get()
                        .map(OpenCDXAnalysisEngineModel::getProtobufMessage)
                        .toList())
                .build();
    }
}
