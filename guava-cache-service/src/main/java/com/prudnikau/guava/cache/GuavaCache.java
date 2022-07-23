package com.prudnikau.guava.cache;

import com.google.common.cache.*;
import com.prudnikau.guava.data.Data;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * <p/>
 * Guava-cache-service 2022  epam.com
 * <p/>
 * Date: 07/20/2022
 *
 * @author Siarhei Prudnikau1
 */
public class GuavaCache {

    static final Logger LOGGER = Logger.getLogger(Cache.class.getName());
    final CacheLoader<String, Data> loader;
    final RemovalListener<String, Data> listener;
    final LoadingCache<String, Data> cache;

    public GuavaCache (long timeSinceLastUse, int maxSize) {
        loader = new CacheLoader<String, Data>() {
            @Override
            public final Data load(final String key) {
                return new Data(key);
            }
        };

        listener = new RemovalListener<String, Data>() {
            @Override
            public void onRemoval(final RemovalNotification<String, Data> n) {
                if (n.wasEvicted()) {
                    final String cause = n.getKey();
                    LOGGER.info("'" + cause + "'" + " has been removed. " + "Cache size = " + size());
                }
            }
        };

        cache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterAccess(timeSinceLastUse, TimeUnit.SECONDS)
                .removalListener(listener)
                .recordStats()
                .build(loader);
    }

    public void getUnchecked(String key) {
        cache.getUnchecked(key);
    }

    public Long size() {
        return cache.size();
    }

    public void cleanUp() {
        cache.cleanUp();
    }

    public Data getIfPresent (String key) {
        return cache.getIfPresent(key);
    }

    public void getStats() {
        long loadSuccessCount = cache.stats().loadSuccessCount();
        StringBuilder sb= new StringBuilder("Statistic: ");
        sb.append("\n");
        sb.append("Total items added to the cache - ");
        sb.append(loadSuccessCount);
        sb.append("\n");
        sb.append("Total removed from cache - ");
        sb.append(cache.stats().evictionCount());
        sb.append("\n");
        sb.append("Average time spent for putting new values into the cache - ");
        sb.append(cache.stats().averageLoadPenalty());
        LOGGER.info(sb.toString());
    }
}
