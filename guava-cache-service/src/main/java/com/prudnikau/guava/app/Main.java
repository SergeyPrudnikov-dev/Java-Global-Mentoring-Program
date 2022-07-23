package com.prudnikau.guava.app;

import com.prudnikau.guava.cache.CacheThread;
import com.prudnikau.guava.cache.GuavaCache;

/**
 * <p/>
 * Guava-cache-service  2022  epam.com
 * <p/>
 * Date: 07/21/2022
 *
 * @author Siarhei Prudnikau1
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        long timeSinceLastUse = 5;
        int maxSize = 100000;
        final GuavaCache cache = new GuavaCache(timeSinceLastUse, maxSize);
        int threads = 10;
        for (int i=0; i<threads; i++) {
            new CacheThread(cache, i).start();
        }
        Thread.sleep(20000);
        cache.cleanUp();
        cache.getStats();
    }
}
