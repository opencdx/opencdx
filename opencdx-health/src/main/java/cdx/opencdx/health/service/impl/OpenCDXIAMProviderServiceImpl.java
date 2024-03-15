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
import cdx.opencdx.grpc.audit.SensitivityLevel;
import cdx.opencdx.grpc.provider.*;
import cdx.opencdx.health.dto.OpenCDXDtoNpiJsonResponse;
import cdx.opencdx.health.model.OpenCDXIAMProviderModel;
import cdx.opencdx.health.repository.OpenCDXIAMProviderRepository;
import cdx.opencdx.health.service.OpenCDXIAMProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
    public static final String PROVIDER_NUMBER = "PROVIDER NUMBER: ";
    public static final String PROVIDER = "PROVIDER ";
    public static final String PROVIDER_ACCESSED = "Provider accessed";
    public static final String FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL =
            "FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL";
    public static final String OBJECT = "OBJECT";

    private final OpenCDXIAMProviderRepository openCDXIAMProviderRepository;

    private final OpenCDXAuditService openCDXAuditService;
    private final ObjectMapper objectMapper;
    private final OpenCDXCountryRepository openCDXCountryRepository;
    private final OpenCDXCurrentUser openCDXCurrentUser;

    /**
     * Provider Service
     * @param  openCDXIAMProviderRepository, Database repository for Provider
     * @param openCDXAuditService Audit Service to record information
     * @param objectMapper ObjectMapper for converting to JSON
     * @param openCDXCountryRepository Country Repository for looking up countries
     * @param openCDXCurrentUser
     */
    public OpenCDXIAMProviderServiceImpl(
            OpenCDXIAMProviderRepository openCDXIAMProviderRepository,
            OpenCDXAuditService openCDXAuditService,
            ObjectMapper objectMapper,
            OpenCDXCountryRepository openCDXCountryRepository,
            OpenCDXCurrentUser openCDXCurrentUser) {
        this.openCDXIAMProviderRepository = openCDXIAMProviderRepository;
        this.openCDXAuditService = openCDXAuditService;
        this.objectMapper = objectMapper;
        this.openCDXCountryRepository = openCDXCountryRepository;
        this.openCDXCurrentUser = openCDXCurrentUser;
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
                .findById(new ObjectId(request.getProviderNumber()))
                .orElseThrow(
                        () -> new OpenCDXNotFound(DOMAIN, 3, "FAILED_TO_FIND_PROVIDER" + request.getProviderNumber()));
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiAccessed(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    PROVIDER_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    model.getId().toHexString(),
                    PROVIDER_NUMBER + model.getNumber(),
                    PROVIDER + model.getId().toHexString(),
                    this.objectMapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL, e);
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
                .findById(new ObjectId(request.getProviderId()))
                .orElseThrow(() -> new OpenCDXNotFound(DOMAIN, 5, "FAILED_TO_FIND_USER" + request.getProviderId()));

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
                    providerModel.getId().toHexString(),
                    providerModel.getUserId(),
                    "PROVIDER" + providerModel.getUserId(),
                    this.objectMapper.writeValueAsString(providerModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 6, "FAILED_TO_CONVERT_OPEN_CDXIAM_USER_MODEL", e);
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
                        "PROVIDER ID: " + model.getId().toHexString(),
                        PROVIDER_NUMBER + model.getNumber(),
                        PROVIDER + model.getId().toHexString(),
                        this.objectMapper.writeValueAsString(model));
            } catch (JsonProcessingException e) {
                OpenCDXNotAcceptable openCDXNotAcceptable =
                        new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL, e);
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
        OpenCDXIAMProviderModel openCDXIAMProviderModel;
        try {
            String npiNumber = request.getUserId();
            String apiUrl = "https://npiregistry.cms.hhs.gov/api/";
            // Construct the URL
            URL url = URI.create(apiUrl + "?version=2.1&number=" + npiNumber).toURL();
            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set the request method
            connection.setRequestMethod("GET");
            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                OpenCDXDtoNpiJsonResponse openCDXDtoNpiJsonResponse =
                        this.objectMapper.readValue(response.toString(), OpenCDXDtoNpiJsonResponse.class);
                if (openCDXDtoNpiJsonResponse != null) {
                    log.info("Response: {}", openCDXDtoNpiJsonResponse);
                    openCDXIAMProviderModel = new OpenCDXIAMProviderModel(
                            openCDXDtoNpiJsonResponse.getResults().get(0), openCDXCountryRepository);
                    loadProviderPiiCreated(request, openCDXIAMProviderModel);
                    return LoadProviderResponse.newBuilder()
                            .setProvider(this.openCDXIAMProviderRepository
                                    .save(openCDXIAMProviderModel)
                                    .getProtobufMessage())
                            .build();
                }
            } else {
                log.error("Error: " + responseCode);
                throw new OpenCDXServiceUnavailable(DOMAIN, 1, "FAILED_TO_LOAD_PROVIDER" + responseCode);
            }
        } catch (Exception e) {
            throw new OpenCDXServiceUnavailable(DOMAIN, 2, "FAILED_TO_LOAD_PROVIDER" + e.getMessage(), e);
        }
        return null;
    }

    private void loadProviderPiiCreated(LoadProviderRequest request, OpenCDXIAMProviderModel openCDXIAMProviderModel) {
        try {
            OpenCDXIAMUserModel currentUser = this.openCDXCurrentUser.getCurrentUser();
            this.openCDXAuditService.piiCreated(
                    currentUser.getId().toHexString(),
                    currentUser.getAgentType(),
                    PROVIDER_ACCESSED,
                    SensitivityLevel.SENSITIVITY_LEVEL_HIGH,
                    openCDXIAMProviderModel.getId().toHexString(),
                    PROVIDER_NUMBER + openCDXIAMProviderModel.getNumber(),
                    PROVIDER + openCDXIAMProviderModel.getId().toHexString(),
                    this.objectMapper.writeValueAsString(openCDXIAMProviderModel));
        } catch (JsonProcessingException e) {
            OpenCDXNotAcceptable openCDXNotAcceptable =
                    new OpenCDXNotAcceptable(DOMAIN, 6, FAILED_TO_CONVERT_OPEN_CDXPROVIDER_USER_MODEL, e);
            openCDXNotAcceptable.setMetaData(new HashMap<>());
            openCDXNotAcceptable.getMetaData().put(OBJECT, request.toString());
            throw openCDXNotAcceptable;
        }
    }
}
