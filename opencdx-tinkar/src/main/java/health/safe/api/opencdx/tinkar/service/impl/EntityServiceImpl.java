/*
 * Copyright 2023 Safe Health Systems, Inc.
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
package health.safe.api.opencdx.tinkar.service.impl;

import cdx.opencdx.commons.annotations.ExcludeFromJacocoGeneratedReport;
import dev.ikm.tinkar.common.service.CachingService;
import dev.ikm.tinkar.common.service.PrimitiveData;
import dev.ikm.tinkar.common.service.ServiceKeys;
import dev.ikm.tinkar.common.service.ServiceProperties;
import dev.ikm.tinkar.entity.Entity;
import health.safe.api.opencdx.tinkar.service.EntityServiceSearch;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@ExcludeFromJacocoGeneratedReport
public class EntityServiceImpl implements EntityServiceSearch {

    private static final String SASTOREOPENNAME = "Open SpinedArrayStore";

    @Value("${data.path.parent}")
    private String pathParent;

    @Value("${data.path.child}")
    private String pathChild;

    @Override
    public String getEntity(int nid) {
        try {
            if (!PrimitiveData.running()) {
                CachingService.clearAll();
                ServiceProperties.set(ServiceKeys.DATA_STORE_ROOT, new File(pathParent, pathChild));
                PrimitiveData.selectControllerByName(SASTOREOPENNAME);
                PrimitiveData.start();
            }

            return Entity.get(nid).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
