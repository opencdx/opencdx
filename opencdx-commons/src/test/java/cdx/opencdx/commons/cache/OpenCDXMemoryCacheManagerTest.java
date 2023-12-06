package cdx.opencdx.commons.cache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.cache.Cache;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(cache instanceof OpenCDXMemoryCache);
        assertEquals(cacheName, cache.getName());
    }
}
