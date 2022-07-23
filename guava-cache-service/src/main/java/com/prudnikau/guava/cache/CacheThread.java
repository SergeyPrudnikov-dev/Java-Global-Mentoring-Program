package com.prudnikau.guava.cache;

import com.prudnikau.guava.data.Data;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * <p/>
 * Guava-cache-service  2022  epam.com
 * <p/>
 * Date: 07/14/2022
 *
 * @author Siarhei Prudnikau1
 */
public class CacheThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(GuavaCache.class.getName());
    private final GuavaCache cache;
    private final int prefix;


    public CacheThread(GuavaCache cache, int prefix) {
        this.cache = cache;
        this.prefix = prefix;
    }

    public void run() {
        for (int i = 0; i < 15000; i++) {
            cache.getUnchecked(prefix + "_" + i + "_" + UUID.randomUUID());
        }
        LOGGER.info(prefix + " thread ");
        cache.cleanUp();
    }
}
