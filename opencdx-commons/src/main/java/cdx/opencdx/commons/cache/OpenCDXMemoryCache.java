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

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.core.serializer.support.SerializationDelegate;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Simple {@link org.springframework.cache.Cache} implementation based on the
 * core JDK {@code java.util.concurrent} package.
 *
 * <p>Useful for testing or simple caching scenarios, typically in combination
 * with {@link org.springframework.cache.support.SimpleCacheManager} or
 * dynamically through {@link org.springframework.cache.concurrent.ConcurrentMapCacheManager}.
 *
 * <p>Note: As {@link ConcurrentMap} only accepts {@code null} values for
 * reference keys, this class will replace them with an internal null holder
 * value. This should not affect any actual store value serializability.
 *
 * <p>As of Spring Framework 4.3, this class is also a
 * {@link org.springframework.cache.support.AbstractValueAdaptingCache} subclass.
 *
 * @since 3.1
 * @see org.springframework.cache.support.SimpleCacheManager
 * @see org.springframework.cache.concurrent.ConcurrentMapCacheManager
 */
public class OpenCDXMemoryCache extends AbstractValueAdaptingCache {

    private static final int MAX_ENTRIES = 1000;
    private final String name;

    @Getter
    @Setter
    private long timeToIdle = 60000L;

    @Getter
    @Setter
    private int maxEntries = MAX_ENTRIES;

    private final ConcurrentMap<Object, CacheValue> store;

    @Nullable private final SerializationDelegate serialization;

    /**
     * Create a new ConcurrentMapCache with the specified name.
     * @param name the name of the cache
     */
    public OpenCDXMemoryCache(String name) {
        this(name, new ConcurrentHashMap<>(256), true, 60000L, null, MAX_ENTRIES);
    }

    /**
     * Create a new ConcurrentMapCache with the specified name.
     * @param name the name of the cache
     * @param allowNullValues whether to accept and convert {@code null}
     * values for this cache
     */
    public OpenCDXMemoryCache(String name, boolean allowNullValues) {
        this(name, new ConcurrentHashMap<>(256), allowNullValues, 60000L, MAX_ENTRIES);
    }

    /**
     * Create a new ConcurrentMapCache with the specified name and the
     * given internal {@link ConcurrentMap} to use.
     * @param name the name of the cache
     * @param store the ConcurrentMap to use as an internal store
     * @param allowNullValues whether to allow {@code null} values
     * (adapting them to an internal null holder value)
     *                        @param timeToIdle the time to live in milliseconds
     * @param maxEntries    the maximum number of entries in the cache
     */
    public OpenCDXMemoryCache(
            String name,
            ConcurrentMap<Object, CacheValue> store,
            boolean allowNullValues,
            long timeToIdle,
            int maxEntries) {
        this(name, store, allowNullValues, timeToIdle, null, maxEntries);
    }

    /**
     * Create a new ConcurrentMapCache with the specified name and the
     * given internal {@link ConcurrentMap} to use. If the
     * {@link SerializationDelegate} is specified,
     * {@link #isStoreByValue() store-by-value} is enabled
     *
     * @param name          the name of the cache
     * @param store         the ConcurrentMap to use as an internal store
     * @param allowNullValues whether to allow {@code null} values
     *                       (adapting them to an internal null holder value)
     * @param timeToIdle    the time to live in milliseconds
     * @param serialization the {@link SerializationDelegate} to use
     *                      to serialize cache entry or {@code null} to store the reference
     * @param maxEntries    the maximum number of entries in the cache
     * @since 4.3
     */
    protected OpenCDXMemoryCache(
            String name,
            ConcurrentMap<Object, CacheValue> store,
            boolean allowNullValues,
            long timeToIdle,
            @Nullable SerializationDelegate serialization,
            int maxEntries) {

        super(allowNullValues);
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(store, "Store must not be null");
        this.name = name;
        this.store = store;
        this.serialization = serialization;
        this.timeToIdle = timeToIdle;
        this.maxEntries = maxEntries;
    }

    /**
     * Return whether this cache stores a copy of each entry ({@code true})
     * or a reference ({@code false}, default).
     * @see #OpenCDXMemoryCache(String, ConcurrentMap, boolean, long, SerializationDelegate, int)
     * @return whether this cache stores a copy of each entry or a reference
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

        cacheValue.updateLastAccessed();
        return (T) cacheValue.getValue();
    }

    /**
     * Return the value to which this cache maps the specified key,
     * generically specifying a type that return value will be cast to.
     * <p>Note: This variant of {@code get} does not allow for differentiating
     * between a cached {@code null} value and no cache entry found at all.
     * Use the standard {@link #get(Object)} variant for that purpose instead.
     * @param key the key whose associated value is to be returned
     * @return the value to which this cache maps the specified key
     * (which may be {@code null} itself), or also {@code null} if
     * the cache contains no mapping for this key
     * @throws IllegalStateException if a cache entry has been found
     * but failed to match the specified type
     * @since 4.0
     * @see #get(Object)
     */
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

    /**
     * Retrieve the value to which this cache maps the specified key,
     * obtaining that value from {@code valueLoader} if necessary.
     * <p>This method provides a simple substitute for the conventional
     * "if cached, return; otherwise create, cache and return" pattern.
     * <pre><code>Object value = cache.get(key, () -&gt; createValue(key));</code></pre>
     * <p>If possible, implementations should ensure that the loading operation
     * is synchronized so that the specified {@code valueLoader} is only called
     * once in case of concurrent access on the same key.
     * <p>If the {@code valueLoader} throws an exception, it is wrapped in a
     * {@link ValueRetrievalException}
     * @param <T> the type of the value being loaded
     * @param key the key whose associated value is to be returned
     * @param valueLoader the value loader to use to load the value if it is not in the cache
     * @return the value to which this cache maps the specified key
     * @throws ValueRetrievalException if the {@code valueLoader} throws an exception
     * @see #get(Object, Callable)
     * @since 4.3
     */
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> retrieve(Object key, Supplier<CompletableFuture<T>> valueLoader) {
        this.cleanUpIdleEntries(Optional.of(key));
        return CompletableFuture.supplyAsync(() -> {
            CacheValue cacheValue = this.store.compute(
                    key, (k, oldValue) -> new CacheValue(valueLoader.get().join()));
            cacheValue.updateLastAccessed();
            return (T) cacheValue.getValue();
        });
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        checkMaxEntries();
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

    /**
     * Clean up entries that have been idle for longer than the configured time to idle.
     * @param keyToPreserve the key to preserve
     */
    public void cleanUpIdleEntries(Optional<Object> keyToPreserve) {
        long currentTime = System.currentTimeMillis();
        long maxIdleTime = currentTime - timeToIdle;

        this.checkMaxEntries();

        store.entrySet().removeIf(entry -> {
            long lastAccessedTime = entry.getValue().getLastAccessed();
            boolean isIdle = lastAccessedTime <= maxIdleTime;
            return isIdle && (keyToPreserve.isEmpty() || !entry.getKey().equals(keyToPreserve.get()));
        });
    }

    private void checkMaxEntries() {
        while (store.size() > maxEntries) {
            // Remove the oldest entry if max entries is reached
            removeOldestEntry();
        }
    }

    private void removeOldestEntry() {
        Optional<Object> oldestKey = store.entrySet().stream()
                .min(Comparator.comparingLong(entry -> entry.getValue().getLastAccessed()))
                .map(Map.Entry::getKey);

        oldestKey.ifPresent(this::evict);
    }

    /**
     * Wrapper object for the value stored in the map.
     */
    public static class CacheValue {
        private final Object value;
        private volatile long lastAccessed;
        /**
         * Create a new CacheValue instance for the given value.
         * @param value the value to cache
         */
        public CacheValue(Object value) {
            this.value = value;
            updateLastAccessed();
        }

        /**
         * Get the value.
         * @return the value
         */
        public Object getValue() {
            return value;
        }

        /**
         * Get the last accessed timestamp.
         * @return the timestamp
         */
        public long getLastAccessed() {
            return lastAccessed;
        }

        /**
         * Update the last accessed timestamp to the current time.
         */
        public void updateLastAccessed() {
            this.lastAccessed = System.currentTimeMillis();
        }
    }
}