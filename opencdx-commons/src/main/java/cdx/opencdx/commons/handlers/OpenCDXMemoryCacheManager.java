package cdx.opencdx.commons.handlers;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class OpenCDXMemoryCacheManager implements CacheManager {

    private ConcurrentHashMap<String, OpenCDXMemoryCache> caches;
    private int timeToIdle = 300000;

    public int getTimeToIdle() {
        return timeToIdle;
    }

    public void setTimeToIdle(int timeToIdle) {
        this.timeToIdle = timeToIdle;
    }

    public OpenCDXMemoryCacheManager() {
        this.caches = new ConcurrentHashMap<>();
    }

    @Override
    public Cache getCache(String name) {
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }

    public class OpenCDXMemoryCache implements Cache {

        @Override
        public String getName() {
            return null;
        }

        @Override
        public Object getNativeCache() {
            return null;
        }

        @Override
        public ValueWrapper get(Object key) {
            return null;
        }

        @Override
        public <T> T get(Object key, Class<T> type) {
            return null;
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            return null;
        }

        @Override
        public void put(Object key, Object value) {

        }

        @Override
        public void evict(Object key) {

        }

        @Override
        public void clear() {

        }
    }
}
