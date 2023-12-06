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
package cdx.opencdx.commons.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

@Slf4j
public class OpenCDXMemoryCacheManager extends ConcurrentMapCacheManager {

    public OpenCDXMemoryCacheManager() {
        log.info("Creating OpenCDXMemoryCacheManager");
    }

    public OpenCDXMemoryCacheManager(String... cacheNames) {
        super(cacheNames);
        log.info("Creating OpenCDXMemoryCacheManager");
    }

    @Override
    protected Cache createConcurrentMapCache(String name) {
        log.info("Creating Cache for {}", name);
        return new OpenCDXMemoryCache(name);
    }
}
