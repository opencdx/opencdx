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
package cdx.opencdx.tinkar.service.impl;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import cdx.opencdx.commons.exceptions.OpenCDXBadRequest;
import cdx.opencdx.tinkar.service.OpenCDXTinkarService;
import dev.ikm.tinkar.common.service.*;
import io.micrometer.observation.annotation.Observed;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Tinkar Service implementation
 */
@Service
@Observed(name = "opencdx")
@ExcludeFromJacocoGeneratedReport
@Slf4j
public class OpenCDXTinkarServiceImpl implements OpenCDXTinkarService {

    private static final String ARRAY_STORE_TO_OPEN = "Open SpinedArrayStore";

    @Value("${data.path.parent}")
    private String pathParent;

    @Value("${data.path.child}")
    private String pathChild;

    /**
     * Default Constructor
     */
    public OpenCDXTinkarServiceImpl() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public PrimitiveDataSearchResult[] search(String query, int maxResultSize) {
        try {
            initializePrimitiveData(pathParent, pathChild);
            return PrimitiveData.get().search(query, maxResultSize);
        } catch (Exception e) {
            throw new OpenCDXBadRequest("OpenCDXTinkarServiceImpl", 1, "Search Failed", e);
        }
    }

    @Override
    public String getEntity(int nid) {
        try {
            initializePrimitiveData(pathParent, pathChild);
            PrimitiveDataSearchResult[] searchResult = PrimitiveData.get().search("nid=" + nid, 1);
            return searchResult[0].toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new OpenCDXBadRequest("OpenCDXTinkarServiceImpl", 1, "Entity Get Failed", e);
        }
    }

    private void initializePrimitiveData(String pathParent, String pathChild) {
        if (!PrimitiveData.running()) {
            CachingService.clearAll();
            ServiceProperties.set(ServiceKeys.DATA_STORE_ROOT, new File(pathParent, pathChild));
            PrimitiveData.selectControllerByName(ARRAY_STORE_TO_OPEN);
            PrimitiveData.start();
        }
    }
}
