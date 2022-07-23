package com.prudnikau.guava.cache;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;

/**
 * //TODO: [before commit] class description.
 * <p/>
 * University-commission 2022  epam.com
 * <p/>
 * Date: 07/20/2022
 *
 * @author Siarhei Prudnikau1
 */
public class GuavaCache {
    long defaultTimeout = 5000;
    int maxSize = 100000;
    int cacheAddCounter = 0;
    int cacheEvictionCounter = 0;
    long startWork = System.currentTimeMillis();

    final CacheLoader<String, Data> loader = new CacheLoader<String, Data>() {
        @Override
        public final Data load(final String key) {
            return new Data(key);
        }
    };

    final RemovalListener<String, String> listener = new RemovalListener<String, String>() {
        @Override
        public void onRemoval(final RemovalNotification<String, String> n) {
            if (n.wasEvicted()) {
                final String cause = n.getCause().name();
                assertEquals(RemovalCause.SIZE.toString(), cause);
            }
        }
    };

    final LoadingCache<String, Data> cache = CacheBuilder.newBuilder().maximumSize(maxSize)
            .expireAfterAccess(defaultTimeout, TimeUnit.MILLISECONDS).build(loader);
}
