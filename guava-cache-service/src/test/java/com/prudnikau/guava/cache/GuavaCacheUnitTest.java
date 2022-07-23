package com.prudnikau.guava.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.prudnikau.guava.data.Data;
import org.junit.Test;

/**
 * <p/>
 * Guava-cache-service  2022  epam.com
 * <p/>
 * Date: 07/19/2022
 *
 * @author Siarhei Prudnikau1
 */
public class GuavaCacheUnitTest {

    @Test
    public void whenEntryIsUsedOnceEvery4SecondsThenItRemainsInCache() throws InterruptedException {
        long timeSinceLastUse = 5;
        int maxSize = 100000;
        Data lastData = new Data("last");
        final GuavaCache cache = new GuavaCache(timeSinceLastUse, maxSize);
        cache.getUnchecked("first");
        cache.getUnchecked("last");

        for (int i =0; i < 3; i++) {
            cache.getUnchecked("last");
            Thread.sleep(4000);
            cache.cleanUp();
        }
        long size = cache.size();
        assertEquals(1, size);
        assertNull(cache.getIfPresent("first"));
        assertEquals(lastData, cache.getIfPresent("last"));
    }

    @Test
    public void whenRecordsNotUsedForMore5Seconds_thenRemovedAll() throws InterruptedException {
        long timeSinceLastUse = 5;
        int maxSize = 100000;
        final GuavaCache cache = new GuavaCache(timeSinceLastUse, maxSize);
        int threads = 10;
        for (int i=0; i<threads; i++) {
            new CacheThread(cache, i).start();
        }
        Thread.sleep(10000);
        cache.cleanUp();
        assertEquals(0, (long)cache.size());
    }
}
