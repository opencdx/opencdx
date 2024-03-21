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

import cdx.opencdx.health.model.OpenCDXMedicationModel;
import java.util.List;

/**
 * OpenCDXApiFDA is an interface to access the OpenFDA API to get the medication information.
 */
public interface OpenCDXApiFDA {
    /**
     * getMedicationsByBrandName is a method to get the medication information by brand name.
     * @param brandNamePrefix String containing the brand name of the medication.
     * @return List of OpenCDXMedicationModel containing the medication information.
     */
    List<OpenCDXMedicationModel> getMedicationsByBrandName(String brandNamePrefix);
}
