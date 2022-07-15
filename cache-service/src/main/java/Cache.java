import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * //TODO: [before commit] class description.
 * <p/>
 * University-commission 2022  epam.com
 * <p/>
 * Date: 07/13/2022
 *
 * @author Siarhei Prudnikau1
 */
public class Cache {
    private static final Logger LOGGER = Logger.getLogger(Cache.class.getName());

    long defaultTimeout = 5000;
    int maxSize = 100000;
    int cacheAddCounter = 0;
    int cacheEvictionCounter = 0;
    long startWork = System.currentTimeMillis();

    private Comparator keyComparator = Comparator.comparingLong(Key::getTimelife)
            .thenComparing(Key::getHashData);
    private volatile ConcurrentSkipListMap<Key, Data> map = new ConcurrentSkipListMap(keyComparator);

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread th = new Thread(r);
        th.setDaemon(true);
        return th;
    });

    public Cache() throws Exception {
        if (defaultTimeout < 10) {
            throw new Exception("Too short interval for storage in the cache. Interval should be more than 10 ms");
        }
        this.scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long current = System.currentTimeMillis();
                for (Key k : map.keySet()) {
                    if (!k.isLive(current)) {
                        Data value = map.remove(k);
                        cacheEvictionCounter++;
                        LOGGER.info(value.getValue() + " was removed from the cache because that value has been cached for more than 5 seconds.");
                    }
                    if (map.isEmpty()){
                        LOGGER.info("map.isEmpty " + getStatistic());
                    }
                }
            }
        }, 1, defaultTimeout, TimeUnit.MILLISECONDS);
    }


    public synchronized void put(Data data) {
        int size = map.size();
        if (size >= maxSize) {
            Map.Entry<Key, Data> entry = map.pollFirstEntry();
            cacheEvictionCounter++;
            LOGGER.info(entry.getValue().getValue() + " has been removed from the cache because" +
                    " its size has reached its maximum value " + size);
        }
        Key key = new Key(data.getValue().hashCode());
        map.put(key, data);
        cacheAddCounter++;
    }


    public Data get(Key key) {
//        Data data = map.get(key);
//        map.remove(key);
//        put(data);
        return map.get(key);
    }

    public int size() {
        return this.map.size();
    }

    public String getStatistic() {
        StringBuilder sb= new StringBuilder("Statistic: ");
        sb.append("Total items added to the cache - ");
        sb.append(cacheAddCounter);
        sb.append(". Total removed from cache - ");
        sb.append(cacheEvictionCounter);
        sb.append("\n");
        sb.append("Average time spent for putting new values into the cache - ");
        double timeAdd = (System.currentTimeMillis() - startWork)/(double)cacheAddCounter;
        sb.append(timeAdd);
        return sb.toString();
    }
}
