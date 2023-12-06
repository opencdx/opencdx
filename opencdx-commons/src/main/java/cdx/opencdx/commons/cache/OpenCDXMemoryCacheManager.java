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
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Simple {@link CacheManager} implementation that lazily builds {@link OpenCDXMemoryCache}
 * instances for each {@link #getCache} request.
 *
 * <p>By default, this implementation creates {@link OpenCDXMemoryCache} instances with a maximum
 * size of 256 and no expiration. This can be overridden by using the appropriate constructor.
 *
 * <p>As this cache manager does not manage any shared resources, it does not implement
 * {@link org.springframework.beans.factory.DisposableBean DisposableBean} or
 * {@link org.springframework.context.Lifecycle Lifecycle}.
 *
 * <p>As of Spring Framework 4.3, this class is also a
 * {@link org.springframework.cache.support.AbstractCacheManager} subclass.
 *
 * @since 4.1
 * @see OpenCDXMemoryCache
 */
public class OpenCDXMemoryCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    /**
     * Create a new ConcurrentMapCacheManager with the default settings.
     */
    public OpenCDXMemoryCacheManager() {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = this.cacheMap.get(name);
        if (cache == null) {
            cache = this.cacheMap.computeIfAbsent(name, this::createConcurrentMapCache);
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }
    /**
     * Create a new ConcurrentMapCache instance for the specified cache name.
     * @param name the name of the cache
     * @return the ConcurrentMapCache (or a decorator thereof)
     */
    protected Cache createConcurrentMapCache(String name) {
        return new OpenCDXMemoryCache(name, new ConcurrentHashMap<>(256), false, 300000L, 1000);
    }
}
