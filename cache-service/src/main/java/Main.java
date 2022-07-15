/**
 * //TODO: [before commit] class description.
 * <p/>
 * University-commission 2022  epam.com
 * <p/>
 * Date: 07/14/2022
 *
 * @author Siarhei Prudnikau1
 */
public class Main {

    public static void main(String[] args) throws Exception {
        final Cache cache = new Cache();

        int threads = 10;

        for (int i=0; i<threads; i++) {
            new CacheThread(cache, i).start();
        }

        Thread.sleep(90000);
        System.out.println("Finish");
    }

}

