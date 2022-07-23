package com.prudnikau.guava.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.prudnikau.cache.Cache;
import com.prudnikau.cache.CacheThread;
import com.prudnikau.data.Data;
import com.prudnikau.data.Key;
import org.junit.Test;

/**
 * <p/>
 * Guava-cache-service  2022  epam.com
 * <p/>
 * Date: 07/19/2022
 *
 * @author Siarhei Prudnikau1
 */
public class CacheUnitTest {

    @Test
    public void entriesWillBeDeletedAfter5SecondsOfLifeRegardlessOfUsage() throws Exception {
        final Cache cache = new Cache();
        Data firstData = new Data("first");
        Data lastData = new Data("last");
        Key firstKey = new Key(firstData.hashCode());
        Key lastKey = new Key(lastData.hashCode());

        cache.put(firstData);
        cache.put(lastData);

        for (int i =0; i < 5; i++) {
            cache.get(firstKey);
            cache.get(lastKey);
            Thread.sleep(1001);
        }
        long size = cache.size();
        assertEquals(0, size);
        assertNull(cache.get(firstKey));
        assertNull(cache.get(lastKey));
    }

    @Test
    public void entriesAreAvailable4_8SecondsAfterCreation() throws Exception {
        final Cache cache = new Cache();
        Data firstData = new Data("first");
        Data lastData = new Data("last");
        Key firstKey = new Key(firstData.hashCode());
        Key lastKey = new Key(lastData.hashCode());

        cache.put(firstData);
        cache.put(lastData);
        Thread.sleep(4800);
        Data expectedFirstData = cache.get(firstKey);
        Data expectedLastData = cache.get(lastKey);

        assertEquals(expectedFirstData,firstData);
        assertEquals(expectedLastData,lastData);
    }

    @Test
    public void numberAttemptsToWriteUniqueValuesEqualToNumberEntriesAddedAndRemoved() throws Exception {
        final Cache cache = new Cache();
        int amountAttempts = 15000;
        int threads = 10;

        int totalAmount = amountAttempts * threads;
        for (int i=0; i<threads; i++) {
            new CacheThread(cache, i, 15000).start();
        }
        Thread.sleep(90000);
        System.out.println("Finish");
        assertEquals(totalAmount, cache.getCacheAddCounter());
        assertEquals(totalAmount, cache.getCacheEvictionCounter());
        assertEquals(0, cache.size());
    }
}
