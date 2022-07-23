package com.prudnikau.cache;

import com.prudnikau.data.Data;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * <p/>
 * Cache-service 2022  epam.com
 * <p/>
 * Date: 07/14/2022
 *
 * @author Siarhei Prudnikau1
 */
public class CacheThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(Cache.class.getName());
    private final Cache cache;
    private final int prefix;
    private final int amount;

    public CacheThread(Cache cache, int prefix, int amount) {
        this.cache = cache;
        this.prefix = prefix;
        this.amount = amount;
    }

    public void run() {
        for (int i = 0; i < amount; i++) {
            Data data = new Data(prefix + "_" + i + "_" + UUID.randomUUID());
            cache.put(data);
        }
        LOGGER.info(prefix + " thread " + cache.getStatistic());
    }
}
