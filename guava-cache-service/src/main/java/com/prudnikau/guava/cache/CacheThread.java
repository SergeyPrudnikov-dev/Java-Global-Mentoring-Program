import java.util.UUID;
import java.util.logging.Logger;

/**
 * //TODO: [before commit] class description.
 * <p/>
 * University-commission 2022  epam.com
 * <p/>
 * Date: 07/14/2022
 *
 * @author Siarhei Prudnikau1
 */
public class CacheThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(Cache.class.getName());
    private final Cache cache;
    private final int prefix;


    public CacheThread(Cache cache, int prefix) {
        this.cache = cache;
        this.prefix = prefix;
    }

    public void run() {
        for (int i = 0; i < 15000; i++) {
            Data data = new Data(prefix + "_" + i + "_" + UUID.randomUUID());
            cache.put(data);
        }
        LOGGER.info(prefix + " thread " + cache.getStatistic());
    }
}
