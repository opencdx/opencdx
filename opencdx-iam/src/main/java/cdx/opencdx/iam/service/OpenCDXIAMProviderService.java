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
package cdx.opencdx.iam.service;

import cdx.opencdx.grpc.provider.*;

/**
 * Interface for the OpenCDXIAMProviderService
 */
public interface OpenCDXIAMProviderService {
    /**
     * Method to get a provider by number.
     * @param request GetProviderRequest for a provider.
     * @return GetProviderResponse with the provider.
     */
    GetProviderResponse getProviderByNumber(GetProviderRequest request);

    /**
     * Method to delete a provider.
     * @param request DeleteProviderRequest for deleting a provider.
     * @return DeleteProviderResponse Response indicating if successful.
     */
    DeleteProviderResponse deleteProvider(DeleteProviderRequest request);

    /**
     * Method to get the list of providers.
     * @return ListProvidersResponse with all the providers.
     */
    ListProvidersResponse listProviders(ListProvidersRequest request);

    /**
     * Method to load all providers.
     * @return LoadProviderResponse with all the providers.
     */
    LoadProviderResponse loadProvider(LoadProviderRequest request);
}
