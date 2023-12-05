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

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

@Slf4j
public class OpenCDXMemoryCacheManager extends ConcurrentMapCacheManager {

    private int timeToIdle = 300000;

    public OpenCDXMemoryCacheManager setTimeToIdle(int timeToIdle) {
        this.timeToIdle = timeToIdle;
        return this;
    }

    public OpenCDXMemoryCacheManager() {
        this.caches = new ConcurrentHashMap<>();
    }

    @Override
    public Cache getCache(String name) {
        OpenCDXMemoryCache cache = this.caches.get(name);
        if (cache == null) {
            synchronized (this.caches) {
                cache = this.caches.get(name);
                if (cache == null) {
                    cache = new OpenCDXMemoryCache(name);
                    this.caches.put(name, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.caches.keySet());
    }

    @Slf4j
    public static class OpenCDXMemoryCache extends ConcurrentMapCache {

        private String name;

        private ConcurrentHashMap<Object, Object> cache;

        public OpenCDXMemoryCache(String name) {
            super(name);
            log.info("Created OpenCDXMemoryCache");
            this.name = name;
            this.cache = new ConcurrentHashMap<>();
        }

        @Override
        protected Object lookup(Object key) {
            log.info("lookup({})", key);
            return super.lookup(key);
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            log.info("get({})", key);
            return super.get(key, valueLoader);
        }

        @Override
        public void put(Object key, Object value) {
            log.info("put({},{})", key, value);
            super.put(key, value);
        }

        @Override
        public ValueWrapper putIfAbsent(Object key, Object value) {
            log.info("putIfAbsent({},{})", key, value);
            return super.putIfAbsent(key, value);
        }
    }
}
