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

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.core.serializer.support.SerializationDelegate;

@Slf4j
public class OpenCDXMemoryCache extends ConcurrentMapCache {

    public static final String CREATED_OPEN_CDX_MEMORY_CACHE = "Created OpenCDXMemoryCache";

    public OpenCDXMemoryCache(String name, boolean allowNullValues) {
        super(name, allowNullValues);
    }

    public OpenCDXMemoryCache(String name, ConcurrentMap<Object, Object> store, boolean allowNullValues) {
        super(name, store, allowNullValues);
        log.info(CREATED_OPEN_CDX_MEMORY_CACHE);
    }

    protected OpenCDXMemoryCache(
            String name,
            ConcurrentMap<Object, Object> store,
            boolean allowNullValues,
            SerializationDelegate serialization) {
        super(name, store, allowNullValues, serialization);
        log.info(CREATED_OPEN_CDX_MEMORY_CACHE);
    }

    public OpenCDXMemoryCache(String name) {
        super(name);
        log.info(CREATED_OPEN_CDX_MEMORY_CACHE);
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
    public Cache.ValueWrapper putIfAbsent(Object key, Object value) {
        log.info("putIfAbsent({},{})", key, value);
        return super.putIfAbsent(key, value);
    }

    @Override
    public void evict(Object key) {
        log.info("evict({})", key);
        super.evict(key);
    }

    @Override
    public boolean evictIfPresent(Object key) {
        log.info("evictIfPresent({})", key);
        return super.evictIfPresent(key);
    }

    @Override
    protected Object toStoreValue(Object userValue) {
        log.info("toStoreValue({})", userValue);
        return super.toStoreValue(userValue);
    }

    @Override
    protected Object fromStoreValue(Object storeValue) {
        log.info("fromStoreValue({})", storeValue);
        return super.fromStoreValue(storeValue);
    }

    @Override
    public ValueWrapper get(Object key) {
        log.info("get({})", key);
        return super.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        log.info("get({},{})", key, type);
        return super.get(key, type);
    }

    @Override
    protected ValueWrapper toValueWrapper(Object storeValue) {
        log.info("toValueWrapper({})", storeValue);
        return super.toValueWrapper(storeValue);
    }
}
