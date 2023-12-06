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

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.core.serializer.support.SerializationDelegate;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

@Slf4j
public class OpenCDXMemoryCache extends AbstractValueAdaptingCache {

    private final String name;

    @SuppressWarnings("java:S1068")
    private long timeToIdle = 60000L;

    private final ConcurrentMap<Object, CacheValue> store;

    @Nullable private final SerializationDelegate serialization;

    /**
     * Create a new ConcurrentMapCache with the specified name.
     * @param name the name of the cache
     */
    public OpenCDXMemoryCache(String name) {
        this(name, new ConcurrentHashMap<>(256), true, 60000L);
    }

    /**
     * Create a new ConcurrentMapCache with the specified name.
     * @param name the name of the cache
     * @param allowNullValues whether to accept and convert {@code null}
     * values for this cache
     */
    public OpenCDXMemoryCache(String name, boolean allowNullValues) {
        this(name, new ConcurrentHashMap<>(256), allowNullValues, 60000L);
    }

    /**
     * Create a new ConcurrentMapCache with the specified name and the
     * given internal {@link ConcurrentMap} to use.
     * @param name the name of the cache
     * @param store the ConcurrentMap to use as an internal store
     * @param allowNullValues whether to allow {@code null} values
     * (adapting them to an internal null holder value)
     */
    public OpenCDXMemoryCache(
            String name, ConcurrentMap<Object, CacheValue> store, boolean allowNullValues, long timeToIdle) {
        this(name, store, allowNullValues, timeToIdle, null);
    }

    /**
     * Create a new ConcurrentMapCache with the specified name and the
     * given internal {@link ConcurrentMap} to use. If the
     * {@link SerializationDelegate} is specified,
     * {@link #isStoreByValue() store-by-value} is enabled
     * @param name the name of the cache
     * @param store the ConcurrentMap to use as an internal store
     * @param allowNullValues whether to allow {@code null} values
     * (adapting them to an internal null holder value)
     * @param timeToIdle the time to live in milliseconds
     * @param serialization the {@link SerializationDelegate} to use
     * to serialize cache entry or {@code null} to store the reference
     * @since 4.3
     */
    protected OpenCDXMemoryCache(
            String name,
            ConcurrentMap<Object, CacheValue> store,
            boolean allowNullValues,
            long timeToIdle,
            @Nullable SerializationDelegate serialization) {

        super(allowNullValues);
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(store, "Store must not be null");
        this.name = name;
        this.store = store;
        this.serialization = serialization;
        this.timeToIdle = timeToIdle;
    }

    /**
     * Return whether this cache stores a copy of each entry ({@code true}) or
     * a reference ({@code false}, default). If store by value is enabled, each
     * entry in the cache must be serializable.
     * @since 4.3
     */
    public final boolean isStoreByValue() {
        return (this.serialization != null);
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final ConcurrentMap<Object, CacheValue> getNativeCache() {
        this.cleanUpIdleEntries(Optional.empty());
        return this.store;
    }

    @Override
    @Nullable protected Object lookup(Object key) {
        this.cleanUpIdleEntries(Optional.of(key));
        CacheValue cacheValue = this.store.get(key);
        if (cacheValue != null) {
            cacheValue.updateLastAccessed();
            return cacheValue.getValue();
        }
        return null;
    }

    @SuppressWarnings({"java:S1181", "unchecked"})
    @Override
    @Nullable public <T> T get(Object key, Callable<T> valueLoader) {
        this.cleanUpIdleEntries(Optional.of(key));
        CacheValue cacheValue = this.store.compute(key, (k, oldValue) -> {
            try {
                T value = valueLoader.call();
                return new CacheValue(value);
            } catch (Throwable ex) {
                throw new ValueRetrievalException(key, valueLoader, ex);
            }
        });
        if (cacheValue != null) {
            cacheValue.updateLastAccessed();
            return (T) cacheValue.getValue();
        }
        return null;
    }

    @SuppressWarnings({"java:S1452", "java:S3358"})
    @Nullable public CompletableFuture<?> retrieve(Object key) {
        this.cleanUpIdleEntries(Optional.of(key));
        CacheValue cacheValue = this.store.get(key);
        if (cacheValue != null) {
            cacheValue.updateLastAccessed();
            return CompletableFuture.completedFuture(
                    isAllowNullValues()
                            ? toValueWrapper(cacheValue.getValue())
                            : fromStoreValue(cacheValue.getValue()));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> retrieve(Object key, Supplier<CompletableFuture<T>> valueLoader) {
        this.cleanUpIdleEntries(Optional.of(key));
        return CompletableFuture.supplyAsync(() -> {
            CacheValue cacheValue = this.store.compute(
                    key, (k, oldValue) -> new CacheValue(valueLoader.get().join()));
            if (cacheValue != null) {
                cacheValue.updateLastAccessed();
                return (T) cacheValue.getValue();
            }
            return null;
        });
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        this.store.put(key, new CacheValue(value));
    }

    @Override
    @Nullable public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        CacheValue existing = this.store.putIfAbsent(key, new CacheValue(value));
        return toValueWrapper(existing);
    }

    @Override
    public void evict(Object key) {
        this.store.remove(key);
    }

    @Override
    public boolean evictIfPresent(Object key) {
        return (this.store.remove(key) != null);
    }

    @Override
    public void clear() {
        this.store.clear();
    }

    @Override
    public boolean invalidate() {
        boolean notEmpty = !this.store.isEmpty();
        this.store.clear();
        return notEmpty;
    }

    @SuppressWarnings("java:S1181")
    @Override
    protected Object toStoreValue(@Nullable Object userValue) {
        Object storeValue = super.toStoreValue(userValue);
        if (this.serialization != null) {
            try {
                return this.serialization.serializeToByteArray(storeValue);
            } catch (Throwable ex) {
                throw new IllegalArgumentException(
                        "Failed to serialize cache value '" + userValue + "'. Does it implement Serializable?", ex);
            }
        } else {
            return storeValue;
        }
    }

    @SuppressWarnings("java:S1181")
    @Override
    protected Object fromStoreValue(@Nullable Object storeValue) {
        if (storeValue != null && this.serialization != null) {
            try {
                return super.fromStoreValue(this.serialization.deserializeFromByteArray((byte[]) storeValue));
            } catch (Throwable ex) {
                throw new IllegalArgumentException("Failed to deserialize cache value '" + storeValue + "'", ex);
            }
        } else {
            return super.fromStoreValue(storeValue);
        }
    }

    public void cleanUpIdleEntries(Optional<Object> keyToPreserve) {
        long currentTime = System.currentTimeMillis();
        long maxIdleTime = currentTime - timeToIdle;

        store.entrySet().removeIf(entry -> {
            long lastAccessedTime = entry.getValue().getLastAccessed();
            boolean isIdle = lastAccessedTime <= maxIdleTime;
            return isIdle && (keyToPreserve.isEmpty() || !entry.getKey().equals(keyToPreserve.get()));
        });
    }

    // Inner class representing a cache value with last accessed timestamp
    private static class CacheValue {
        private final Object value;
        private volatile long lastAccessed;

        public CacheValue(Object value) {
            this.value = value;
            updateLastAccessed();
        }

        public Object getValue() {
            return value;
        }

        public long getLastAccessed() {
            return lastAccessed;
        }

        public void updateLastAccessed() {
            this.lastAccessed = System.currentTimeMillis();
        }
    }
}
