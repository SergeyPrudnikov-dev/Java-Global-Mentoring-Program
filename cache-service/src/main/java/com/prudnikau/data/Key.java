package com.prudnikau.data;

/**
 * <p/>
 * Cache-service 2022  epam.com
 * <p/>
 * Date: 07/13/2022
 *
 * @author Siarhei Prudnikau1
 */
public class Key {
    private int hashData;
    private long timelife;
    private final long timeout = 5000;

    public Key(int hashData) {
        this.hashData = hashData;
        this.timelife = System.currentTimeMillis() + timeout;
    }

    public int getHashData() {
        return hashData;
    }

    public boolean isLive(long currentTimeMillis) {
        return currentTimeMillis < this.timelife;
    }

    public long getTimelife() {
        return timelife;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;

        Key key = (Key) o;

        if (getHashData() != key.getHashData()) return false;
        if (getTimelife() != key.getTimelife()) return false;
        return timeout == key.timeout;
    }

    @Override
    public int hashCode() {
        int result = getHashData();
        result = 31 * result + (int) (getTimelife() ^ (getTimelife() >>> 32));
        result = 31 * result + (int) (timeout ^ (timeout >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("com.prudnikau.data.Key{");
        sb.append("key=").append(hashData);
        sb.append(", timelife=").append(timelife);
        sb.append('}');
        return sb.toString();
    }
}
