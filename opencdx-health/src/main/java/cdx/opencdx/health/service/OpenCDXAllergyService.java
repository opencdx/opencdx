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
package cdx.opencdx.health.service;

import cdx.opencdx.grpc.service.health.*;

/**
 * Interface for the OpenCDXAllergyService
 */
public interface OpenCDXAllergyService {
    /**
     * Method to create Known Allergy.
     * @param request CreateAllergyRequest for allergy.
     * @return CreateAllergyResponse with allergy.
     */
    CreateAllergyResponse createAllergy(CreateAllergyRequest request);

    /**
     * Method to get Allergy.
     * @param request GetAllergyRequest for allergy.
     * @return GetAllergyResponse with allergy.
     */
    GetAllergyResponse getAllergy(GetAllergyRequest request);

    /**
     * Method to update Allergy.
     * @param request UpdateAllergyRequest for allergy.
     * @return UpdateAllergyResponse with allergy.
     */
    UpdateAllergyResponse updateAllergy(UpdateAllergyRequest request);

    /**
     * Method to delete Allergy.
     * @param request DeleteAllergyRequest for allergy.
     * @return SuccessResponse with allergy.
     */
    SuccessResponse deleteAllergy(DeleteAllergyRequest request);

    /**
     * Method to list Known Allergy.
     * @param request ListAllergyRequest for allergy.
     * @return ListAllergyResponse with allergy.
     */
    ListAllergyResponse listAllergies(ListAllergyRequest request);
}
