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
package cdx.opencdx.media.repository;

import cdx.opencdx.commons.data.OpenCDXRepository;
import cdx.opencdx.commons.model.OpenCDXMediaModel;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Media Collection
 */
@Repository
@Observed(name = "opencdx")
public interface OpenCDXMediaRepository extends OpenCDXRepository<OpenCDXMediaModel> {}
