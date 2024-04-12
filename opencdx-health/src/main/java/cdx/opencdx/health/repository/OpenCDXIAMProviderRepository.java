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
package cdx.opencdx.health.repository;

import cdx.opencdx.commons.data.OpenCDXRepository;
import cdx.opencdx.health.model.OpenCDXIAMProviderModel;
import io.micrometer.observation.annotation.Observed;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Interface for the Database provider model.
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXIAMProviderRepository extends OpenCDXRepository<OpenCDXIAMProviderModel> {
    /**
     * Find the provider by the NPI number.
     *
     * @param npi NPI number to search for.
     * @return Provider model if found.
     */
    Optional<OpenCDXIAMProviderModel> findByNpiNumber(String npi);

    /**
     * Check if a provider exists by the NPI number.
     *
     * @param npi NPI number to search for.
     * @return True if the provider exists.
     */
    boolean existsByNpiNumber(String npi);
}
