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
package cdx.opencdx.logistics.service;

import cdx.opencdx.logistics.dto.OpenCDXShippingRequest;
import cdx.opencdx.logistics.dto.OpenCDXShippingResponse;
import cdx.opencdx.logistics.model.OpenCDXShippingModel;
import java.util.List;

/**
 * OpenCDX shipping vendor interface
 * Used to define the contract for shipping vendors
 */
public interface OpenCDXShippingVendor {

    /**
     * Get the vendor id
     * @return the vendor id
     */
    String getVendorId();

    /**
     * Get the shipping vendors
     * @param request the shipping request
     * @return the list of shipping vendors
     */
    List<OpenCDXShippingModel> getShippingVendors(OpenCDXShippingRequest request);

    /**
     * Ship the package
     * @param request the shipping request
     * @return the shipping response
     */
    OpenCDXShippingResponse shipPackage(OpenCDXShippingModel request);
}
