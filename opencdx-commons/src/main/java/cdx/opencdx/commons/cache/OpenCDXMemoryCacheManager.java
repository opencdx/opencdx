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
package cdx.opencdx.commons.cache;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

@Slf4j
public class OpenCDXMemoryCacheManager implements CacheManager {

    public static final String CREATED_OPEN_CDX_MEMORY_CACHE_MANAGER = "Created OpenCDXMemoryCacheManager";

    private final ConcurrentMapCacheManager concurrentMapCacheManager;

    public OpenCDXMemoryCacheManager() {
        concurrentMapCacheManager = new ConcurrentMapCacheManager();
        log.info(CREATED_OPEN_CDX_MEMORY_CACHE_MANAGER);
    }

    @Override
    public Cache getCache(String name) {
        log.info("getCache({})", name);
        return concurrentMapCacheManager.getCache(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        log.info("getCacheNames()");
        return concurrentMapCacheManager.getCacheNames();
    }
}
