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

import cdx.opencdx.commons.exceptions.OpenCDXNotAcceptable;
import cdx.opencdx.commons.exceptions.OpenCDXNotFound;
import cdx.opencdx.commons.exceptions.OpenCDXServiceUnavailable;
import cdx.opencdx.commons.model.OpenCDXIAMUserModel;
import cdx.opencdx.commons.repository.OpenCDXCountryRepository;
import cdx.opencdx.commons.service.OpenCDXAuditService;
import cdx.opencdx.commons.service.OpenCDXCurrentUser;
import cdx.opencdx.grpc.service.health.*;
import cdx.opencdx.grpc.types.ProviderStatus;
import cdx.opencdx.grpc.types.SensitivityLevel;
import cdx.opencdx.health.dto.npi.OpenCDXDtoNpiJsonResponse;
import cdx.opencdx.health.feign.OpenCDXNpiRegistryClient;
import cdx.opencdx.health.model.OpenCDXIAMProviderModel;
import cdx.opencdx.health.repository.OpenCDXIAMProviderRepository;
import cdx.opencdx.health.service.OpenCDXIAMProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import io.micrometer.observation.annotation.Observed;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of the IAM Provider Service.
 */
@Slf4j
@Service
@Observed(name = "opencdx")
@SuppressWarnings({"java:S1854", "java:S125", "java:S1481"})
public class OpenCDXIAMProviderServiceImpl implements OpenCDXIAMProviderService {

    private static final String DOMAIN = "OpenCDXIAMProviderServiceImpl";
    private static final String PROVIDER_NUMBER = "PROVIDER NUMBER: ";
    private static final String PROVIDER = "PROVIDER ";
    private static final String PROVIDER_ACCESSED = "Provider accessed";
    private static final String FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL =
            "FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL";
    private static final String OBJECT = "OBJECT";

    private final OpenCDXIAMProviderRepository openCDXIAMProviderRepository;

    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCountryRepository openCDXCountryRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    private final OpenCDXNpiRegistryClient openCDXNpiRegistryClient;

    /**
     * Provider Service
     * @param  openCDXIAMProviderRepository, Database repository for Provider
     * @param openCDXAuditService Audit Service to record information
     * @param objectMapper ObjectMapper for converting to JSON
     * @param openCDXCountryRepository Country Repository for looking up countries
     * @param openCDXCurrentUser Current User Service
     * @param openCDXNpiRegistryClient NPI Registry Client
     */
    public OpenCDXIAMProviderServiceImpl(
            OpenCDXIAMProviderRepository openCDXIAMProviderRepository,
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXCountryRepository openCDXCountryRepository,
            OpenCDXCurrentUser openCDXCurrentUser,
            OpenCDXNpiRegistryClient openCDXNpiRegistryClient) {
        this.openCDXIAMProviderRepository = openCDXIAMProviderRepository;
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCountryRepository = openCDXCountryRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
        this.openCDXNpiRegistryClient = openCDXNpiRegistryClient;
    }

    /**
     * Method to get a provider by number.
     *
     * @param request GetProviderRequest for a provider.
     * @return GetProviderResponse with the provider.
     */
    @Override
    public GetProviderResponse getProviderByNumber(GetProviderRequest request) {
        OpenCDXIAMProviderModel model = this.openCDXIAMProviderRepository
                .findByNpiNumber(request.getProviderNumber())
                .orElseThrow(
                        () -> new OpenCDXNotFound(DOMAIN, 1, "FAILED_TO_FIND_PROVIDER" + request.getProviderNumber()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    PROVIDER_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    PROVIDER_NUMBER + model.getNpiNumber(),
                    PROVIDER_NUMBER + model.getNpiNumber(),
                    PROVIDER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 2, FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return GetProviderResponse.newBuilder()
                .setProvider(model.getProtobufMessage())
                .build();
    }

    /**
     * Method to delete a provider.
     *
     * @param request DeleteProviderRequest for deleting a provider.
     * @return DeleteProviderResponse Response indicating if successful.
     */
    @Override
    public DeleteProviderResponse deleteProvider(DeleteProviderRequest request) {
        OpenCDXIAMProviderModel providerModel = this.openCDXIAMProviderRepository
                .findByNpiNumber(request.getProviderId())
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 8, "FAILED_TO_FIND_USER" + request.getProviderId()));

        providerModel.setStatus(ProviderStatus.DELETED);

        providerModel = this.openCDXIAMProviderRepository.save(providerModel);
        log.info("Deleted User: {}", request.getProviderId());

        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiDeleted(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    "Provider deleted",
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    PROVIDER_NUMBER + providerModel.getNpiNumber(),
                    PROVIDER_NUMBER + providerModel.getNpiNumber(),
                    PROVIDER + providerModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(providerModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 3, "FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL", e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
        return DeleteProviderResponse.newBuilder().build();
    }

    /**
     * Method to get the list of providers.
     * @param request ListProvidersRequest for the list of providers.
     * @return ListProvidersResponse with all the providers.
     */
    @Override
    public ListProvidersResponse listProviders(ListProvidersRequest request) {
        List<OpenCDXIAMProviderModel> all = this.openCDXIAMProviderRepository.findAll();
        OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
        all.forEach(model -> {
            try {
                this.openCDXAuditService.piiAccessed(
                        currentUser.getId().toHexString(),
                        currentUser.getAgentType(),
                        PROVIDER_ACCESSED,
                        SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                        PROVIDER_NUMBER + model.getNpiNumber(),
                        PROVIDER_NUMBER + model.getNpiNumber(),
                        PROVIDER + model.getId().toHexString(),
                        this.objectMapper.writeValueAsString(model));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 4, FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL, e);
                openCDXNotAcceptable.setMetaData(new HashMap<>());
                openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
                throw openCDXNotAcceptable;
            }
        });
        return ListProvidersResponse.newBuilder()
                .addAllResults(all.stream()
                        .map(OpenCDXIAMProviderModel::getProtobufMessage)
                        .toList())
                .build();
    }

    /**
     * Method to load all providers.
     *
     * @param request LoadProvider request
     * @return LoadProviderResponse with all the providers.
     */
    @Override
    public LoadProviderResponse loadProvider(LoadProviderRequest request) {
        Optional<OpenCDXIAMProviderModel> provider =
                this.openCDXIAMProviderRepository.findByNpiNumber(request.getProviderNumber());
        if (provider.isPresent()) {
            return LoadProviderResponse.newBuilder()
                    .setProvider(provider.get().getProtobufMessage())
                    .build();
        }

        OpenCDXIAMProviderModel openCDXIAMProviderModel;
        try {
            ResponseEntity<OpenCDXDtoNpiJsonResponse> response = this.openCDXNpiRegistryClient.getProviderInfo(
                    OpenCDXNpiRegistryClient.NPI_VERSION, request.getProviderNumber());

            if (response.getStatusCode().is2xxSuccessful()) {
                OpenCDXDtoNpiJsonResponse openCDXDtoNpiJsonResponse = response.getBody();

                if (openCDXDtoNpiJsonResponse != null
                        && openCDXDtoNpiJsonResponse.getResults() != null
                        && !openCDXDtoNpiJsonResponse.getResults().isEmpty()) {
                    openCDXIAMProviderModel = new OpenCDXIAMProviderModel(
                            openCDXDtoNpiJsonResponse.getResults().getFirst(), openCDXCountryRepository);
                    if (openCDXIAMProviderRepository.existsByNpiNumber(openCDXIAMProviderModel.getNpiNumber())) {
                        Optional<OpenCDXIAMProviderModel> model =
                                openCDXIAMProviderRepository.findByNpiNumber(openCDXIAMProviderModel.getNpiNumber());
                        if (model.isPresent()) {
                            return LoadProviderResponse.newBuilder()
                                    .setProvider(openCDXIAMProviderModel.getProtobufMessage())
                                    .build();
                        }
                    }
                    openCDXIAMProviderModel = this.openCDXIAMProviderRepository.save(openCDXIAMProviderModel);
                    loadProviderPiiCreated(request, openCDXIAMProviderModel);
                    return LoadProviderResponse.newBuilder()
                            .setProvider(openCDXIAMProviderModel.getProtobufMessage())
                            .build();
                }
            }

            throw new OpenCDXServiceUnavailable(DOMAIN, 5, "FAILED_TO_LOAD_PROVIDER");

        } catch (FeignException e) {
            throw new OpenCDXServiceUnavailable(DOMAIN, 5, "FAILED_TO_LOAD_PROVIDER" + e.getMessage(), e);
        }
    }

    private void loadProviderPiiCreated(LoadProviderRequest request, OpenCDXIAMProviderModel openCDXIAMProviderModel) {
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    PROVIDER_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    PROVIDER_NUMBER + openCDXIAMProviderModel.getNpiNumber(),
                    PROVIDER_NUMBER + openCDXIAMProviderModel.getNpiNumber(),
                    PROVIDER + openCDXIAMProviderModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXIAMProviderModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 7, FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
    }
}
