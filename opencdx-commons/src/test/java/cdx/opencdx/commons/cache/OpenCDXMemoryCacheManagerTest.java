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
package cdx.opencdx.commons.cache;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;

class OpenCDXMemoryCacheManagerTest {

    private OpenCDXMemoryCacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager = new OpenCDXMemoryCacheManager();
    }

    @Test
    void getCache_shouldReturnNonNullCache() {
        String cacheName = "testCache";
        Cache cache = cacheManager.getCache(cacheName);

        assertNotNull(cache);
        assertEquals(cacheName, cache.getName());
    }

    @Test
    void getCache_multipleCallsWithSameName_shouldReturnSameCacheInstance() {
        String cacheName = "testCache";
        Cache cache1 = cacheManager.getCache(cacheName);
        Cache cache2 = cacheManager.getCache(cacheName);

        assertSame(cache1, cache2);
    }

    @Test
    void getCacheNames_shouldReturnEmptySetInitially() {
        assertTrue(cacheManager.getCacheNames().isEmpty());
    }

    @Test
    void getCacheNames_afterCreatingCache_shouldReturnCacheName() {
        String cacheName = "testCache";
        cacheManager.getCache(cacheName);

        assertTrue(cacheManager.getCacheNames().contains(cacheName));
    }

    @Test
    void createConcurrentMapCache_shouldReturnOpenCDXMemoryCacheInstance() {
        String cacheName = "testCache";
        Cache cache = cacheManager.createConcurrentMapCache(cacheName);

        assertNotNull(cache);
        assertInstanceOf(OpenCDXMemoryCache.class, cache);
        assertEquals(cacheName, cache.getName());
    }
}
