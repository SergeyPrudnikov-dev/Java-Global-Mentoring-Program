package com.prudnikau.data;

/**
 * <p/>
 * Cache-service 2022  epam.com
 * <p/>
 * Date: 07/14/2022
 *
 * @author Siarhei Prudnikau1
 */
public class Data {

    private String value;

    public Data(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Data)) return false;
        Data data = (Data) o;
        return getValue().equals(data.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("com.prudnikau.data.Data{");
        sb.append("value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
