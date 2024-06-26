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
import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for communicating with the Allergy service.
 */
public interface OpenCDXAllergyClient {
    /**
     * Method to create Known Allergy.
     * @param request CreateAllergyRequest for allergy.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return CreateAllergyResponse with allergy.
     */
    CreateAllergyResponse createAllergy(CreateAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to get Allergy.
     * @param request GetAllergyRequest for allergy.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return GetAllergyResponse with allergy.
     */
    GetAllergyResponse getAllergy(GetAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to update Allergy.
     * @param request UpdateAllergyRequest for allergy.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return UpdateAllergyResponse with allergy.
     */
    UpdateAllergyResponse updateAllergy(UpdateAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to delete Allergy.
     * @param request DeleteAllergyRequest for allergy.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return SuccessResponse with allergy.
     */
    SuccessResponse deleteAllergy(DeleteAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;

    /**
     * Method to list Known Allergy.
     * @param request ListAllergyRequest for allergy.
     * @param openCDXCallCredentials OpenCDXCallCredentials for authentication.
     * @return ListAllergyResponse with allergy.
     */
    ListAllergyResponse listAllergies(ListAllergyRequest request, OpenCDXCallCredentials openCDXCallCredentials)
            throws OpenCDXClientException;
}
