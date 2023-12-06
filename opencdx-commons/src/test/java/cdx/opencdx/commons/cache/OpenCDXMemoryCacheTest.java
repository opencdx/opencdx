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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cache.Cache;
import org.springframework.core.serializer.support.SerializationDelegate;

@SuppressWarnings("java:S2925")
class OpenCDXMemoryCacheTest {

    @Test
    void testCachePutAndGet() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        cache.put(key, value);
        assertEquals(value, cache.get(key).get());
    }

    @Test
    void testCachePutIfAbsent() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        assertNull(cache.putIfAbsent(key, value));
        assertEquals(value, cache.get(key).get());

        // Attempt to put if absent when the key is already present
        String newValue = "newValue";
        assertNotNull(cache.putIfAbsent(key, newValue));
        assertEquals(value, cache.get(key).get()); // Should not have changed
    }

    @Test
    void testCacheEviction() throws InterruptedException {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache", false);
        cache.setMaxEntries(3);

        // Add entries
        cache.put("key1", "value1");
        Thread.sleep(1000);
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        // Add one more entry to trigger eviction
        cache.put("key4", "value4");

        // Key1 should be evicted since it was the oldest
        assertNull(cache.get("key1"));
        assertNotNull(cache.get("key2"));
        assertNotNull(cache.get("key3"));
        assertNotNull(cache.get("key4"));
    }

    @Test
    void testCacheEvictionWithCustomTimeToIdle() throws InterruptedException {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache", false);
        cache.setMaxEntries(3);
        cache.setTimeToIdle(1000);

        // Add entries
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        // Wait for entries to become idle
        TimeUnit.MILLISECONDS.sleep(1500);

        // Add one more entry to trigger eviction
        cache.put("key4", "value4");

        // Key1 should be evicted since it was the oldest and idle
        assertNull(cache.get("key1"));
        assertNull(cache.get("key2"));
        assertNull(cache.get("key3"));
        assertNotNull(cache.get("key4"));
    }

    @Test
    void testCacheValueLoader() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        String result = cache.get(key, () -> value.toUpperCase());
        assertEquals(value.toUpperCase(), result);
        assertEquals(value.toUpperCase(), cache.get(key).get()); // Ensure value is cached
    }

    @Test
    void testCacheAsyncValueLoader() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        CompletableFuture<String> futureResult =
                cache.retrieve(key, () -> CompletableFuture.completedFuture(value.toUpperCase()));
        assertEquals(value.toUpperCase(), futureResult.join());
        assertEquals(value.toUpperCase(), cache.get(key).get()); // Ensure value is cached
    }

    @Test
    void testCacheAsyncValueLoaderException() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";

        assertThrows(Cache.ValueRetrievalException.class, () -> {
            cache.get(key, (Callable<String>) () -> {
                throw new RuntimeException("Simulated exception");
            });
        });
    }

    @Test
    void testCacheEvict() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        cache.put(key, value);
        assertNotNull(cache.get(key));

        cache.evict(key);
        assertNull(cache.get(key));
    }

    @Test
    void testCacheEvictIfPresent() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        cache.put(key, value);
        assertTrue(cache.evictIfPresent(key));
        assertNull(cache.get(key));
        assertFalse(cache.evictIfPresent("nonexistentKey"));
    }

    @Test
    void testCacheClear() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        cache.put(key, value);
        assertFalse(cache.getNativeCache().isEmpty());

        cache.clear();
        assertTrue(cache.getNativeCache().isEmpty());
    }

    @Test
    void testCacheInvalidate() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        cache.put(key, value);
        assertTrue(cache.invalidate());
        assertTrue(cache.getNativeCache().isEmpty());
        assertFalse(cache.invalidate());
    }

    @Test
    void testRetrieveWithExistingKey() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        cache.put(key, value);

        CompletableFuture<?> future = cache.retrieve(key);

        assertNotNull(future);
        Cache.ValueWrapper wrapper = (Cache.ValueWrapper) future.join();
        assertEquals(value, wrapper.get());
    }

    @Test
    void testRetrieveWithNonexistentKey() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "nonexistentKey";

        CompletableFuture<?> future = cache.retrieve(key);

        assertNull(future);
    }

    @Test
    void testRetrieveWithNullValue() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";

        // Mock a CacheValue with null value
        OpenCDXMemoryCache.CacheValue cacheValue = mock(OpenCDXMemoryCache.CacheValue.class);
        Mockito.when(cacheValue.getValue()).thenReturn(null);

        // Put the mock CacheValue in the cache
        cache.getNativeCache().put(key, cacheValue);

        CompletableFuture<?> future = cache.retrieve(key);

        assertNotNull(future);
        assertNull(future.join());
    }

    @Test
    void testRetrieveWithNonNullValue() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key = "key";
        String value = "value";

        // Mock a CacheValue with a non-null value
        OpenCDXMemoryCache.CacheValue cacheValue = mock(OpenCDXMemoryCache.CacheValue.class);
        Mockito.when(cacheValue.getValue()).thenReturn(value);

        // Put the mock CacheValue in the cache
        cache.getNativeCache().put(key, cacheValue);

        CompletableFuture<?> future = cache.retrieve(key);

        assertNotNull(future);
        Cache.ValueWrapper wrapper = (Cache.ValueWrapper) future.join();
        assertEquals(value, wrapper.get());
    }

    @Test
    void testRetrieveWithAllowNullValuesFalse() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache", false);
        String key = "key";
        String value = "value";

        // Mock a CacheValue with a non-null value
        OpenCDXMemoryCache.CacheValue cacheValue = mock(OpenCDXMemoryCache.CacheValue.class);
        Mockito.when(cacheValue.getValue()).thenReturn(value);

        // Put the mock CacheValue in the cache
        cache.getNativeCache().put(key, cacheValue);

        CompletableFuture<?> future = cache.retrieve(key);

        assertNotNull(future);
        assertEquals(value, future.join());
    }

    // Unit Test for isStoringByValue
    @Test
    void testIsStoreByValue() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        assertFalse(cache.isStoreByValue());
    }

    @Test
    void testToStoreValueWithSerialization() throws IOException {
        SerializationDelegate serializationDelegate = mock(SerializationDelegate.class);
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache(
                "testCache", new ConcurrentHashMap<>(256), false, 300000L, serializationDelegate, 1000);

        String userValue = "value";
        byte[] serializedValue = {1, 2, 3}; // Mock serialized value

        Mockito.when(serializationDelegate.serializeToByteArray(userValue)).thenReturn(serializedValue);

        Object result = cache.toStoreValue(userValue);

        assertNotNull(result);
        assertArrayEquals(serializedValue, (byte[]) result);
    }

    @Test
    void testToStoreValueWithoutSerialization() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String userValue = "value";

        Object result = cache.toStoreValue(userValue);

        assertNotNull(result);
        assertEquals(userValue, result);
    }

    @Test
    void testToStoreValueWithSerializationException() throws IOException {
        SerializationDelegate serializationDelegate = mock(SerializationDelegate.class);
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache(
                "testCache", new ConcurrentHashMap<>(256), false, 300000L, serializationDelegate, 1000);

        String userValue = "value";

        Mockito.when(serializationDelegate.serializeToByteArray(userValue))
                .thenThrow(new RuntimeException("Serialization error"));

        assertThrows(IllegalArgumentException.class, () -> cache.toStoreValue(userValue));
    }

    @Test
    void testFromStoreValueWithSerialization() throws IOException {
        SerializationDelegate serializationDelegate = mock(SerializationDelegate.class);
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache(
                "testCache", new ConcurrentHashMap<>(256), false, 300000L, serializationDelegate, 1000);

        String deserializedValue = "value";
        byte[] serializedValue = {1, 2, 3}; // Mock serialized value

        Mockito.when(serializationDelegate.deserializeFromByteArray(serializedValue))
                .thenReturn(deserializedValue);

        Object result = cache.fromStoreValue(serializedValue);

        assertNotNull(result);
        assertEquals(deserializedValue, result);

        assertTrue(cache.isStoreByValue());
    }

    @Test
    void testFromStoreValueWithoutSerialization() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String storeValue = "value";

        Object result = cache.fromStoreValue(storeValue);

        assertNotNull(result);
        assertEquals(storeValue, result);
    }

    @Test
    void testFromStoreValueNull() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");

        Object result = cache.fromStoreValue(null);

        assertNull(result);
    }

    @Test
    void testFromStoreValueWithSerializationException() throws IOException {
        SerializationDelegate serializationDelegate = mock(SerializationDelegate.class);
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache(
                "testCache", new ConcurrentHashMap<>(256), false, 300000L, serializationDelegate, 1000);

        byte[] serializedValue = {1, 2, 3}; // Mock serialized value

        Mockito.when(serializationDelegate.deserializeFromByteArray(serializedValue))
                .thenThrow(new RuntimeException("Deserialization error"));

        assertThrows(IllegalArgumentException.class, () -> cache.fromStoreValue(serializedValue));
    }

    @Test
    void testCleanUpIdleEntriesWithoutKeyToPreserve() throws InterruptedException {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        cache.setTimeToIdle(750);
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";

        // Mock entries with different last accessed times
        OpenCDXMemoryCache.CacheValue cacheValue1 = mock(OpenCDXMemoryCache.CacheValue.class);
        OpenCDXMemoryCache.CacheValue cacheValue2 = mock(OpenCDXMemoryCache.CacheValue.class);
        OpenCDXMemoryCache.CacheValue cacheValue3 = mock(OpenCDXMemoryCache.CacheValue.class);

        Mockito.when(cacheValue1.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 1000); // Last accessed 1 second ago
        Mockito.when(cacheValue2.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 500); // Last accessed 0.5 seconds ago
        Mockito.when(cacheValue3.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 2000); // Last accessed 2 seconds ago

        cache.getNativeCache().put(key1, cacheValue1);
        cache.getNativeCache().put(key2, cacheValue2);
        cache.getNativeCache().put(key3, cacheValue3);

        cache.cleanUpIdleEntries(Optional.empty());

        assertNull(cache.getNativeCache().get(key1));
        assertNotNull(cache.getNativeCache().get(key2));
        assertNull(cache.getNativeCache().get(key3));
    }

    @Test
    void testCleanUpIdleEntriesWithKeyToPreserve() throws InterruptedException {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        cache.setTimeToIdle(750);
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";

        // Mock entries with different last accessed times
        OpenCDXMemoryCache.CacheValue cacheValue1 = mock(OpenCDXMemoryCache.CacheValue.class);
        OpenCDXMemoryCache.CacheValue cacheValue2 = mock(OpenCDXMemoryCache.CacheValue.class);
        OpenCDXMemoryCache.CacheValue cacheValue3 = mock(OpenCDXMemoryCache.CacheValue.class);

        Mockito.when(cacheValue1.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 1000); // Last accessed 1 second ago
        Mockito.when(cacheValue2.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 500); // Last accessed 0.5 seconds ago
        Mockito.when(cacheValue3.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 2000); // Last accessed 2 seconds ago

        cache.getNativeCache().put(key1, cacheValue1);
        cache.getNativeCache().put(key2, cacheValue2);
        cache.getNativeCache().put(key3, cacheValue3);

        cache.cleanUpIdleEntries(Optional.of(key2)); // Preserve key2

        assertNull(cache.getNativeCache().get(key1));
        assertNotNull(cache.getNativeCache().get(key2));
        assertNull(cache.getNativeCache().get(key3));
    }

    @Test
    void testCleanUpIdleEntriesWithMaxIdleTime() throws InterruptedException {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key1 = "key1";
        String key2 = "key2";

        // Mock entries with different last accessed times
        OpenCDXMemoryCache.CacheValue cacheValue1 = mock(OpenCDXMemoryCache.CacheValue.class);
        OpenCDXMemoryCache.CacheValue cacheValue2 = mock(OpenCDXMemoryCache.CacheValue.class);

        Mockito.when(cacheValue1.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 1000); // Last accessed 1 second ago
        Mockito.when(cacheValue2.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 200000); // Last accessed 200 seconds ago

        cache.getNativeCache().put(key1, cacheValue1);
        cache.getNativeCache().put(key2, cacheValue2);

        cache.setTimeToIdle(100); // Set max idle time to 100 milliseconds

        TimeUnit.MILLISECONDS.sleep(200); // Wait for entries to exceed max idle time

        cache.cleanUpIdleEntries(Optional.empty());

        assertNull(cache.getNativeCache().get(key1));
        assertNull(cache.getNativeCache().get(key2));
    }

    @Test
    void testCleanUpIdleEntriesWithNonEmptyCache() {
        OpenCDXMemoryCache cache = new OpenCDXMemoryCache("testCache");
        String key1 = "key1";
        String key2 = "key2";

        // Mock entries with the same last accessed time
        OpenCDXMemoryCache.CacheValue cacheValue1 = mock(OpenCDXMemoryCache.CacheValue.class);
        OpenCDXMemoryCache.CacheValue cacheValue2 = mock(OpenCDXMemoryCache.CacheValue.class);

        Mockito.when(cacheValue1.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 1000); // Last accessed 1 second ago
        Mockito.when(cacheValue2.getLastAccessed())
                .thenReturn(System.currentTimeMillis() - 1000); // Last accessed 1 second ago

        cache.getNativeCache().put(key1, cacheValue1);
        cache.getNativeCache().put(key2, cacheValue2);

        cache.cleanUpIdleEntries(Optional.empty());

        assertNotNull(cache.getNativeCache().get(key1));
        assertNotNull(cache.getNativeCache().get(key2));
    }
}
