package gov.nystax.nimbus.flink;
public class Metric {
    public String instance;
    public long timestamp;
    public double value;

    public Metric(String instance, long timestamp, double value) {
        this.instance = instance;
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Metric{instance='" + instance + "', timestamp=" + timestamp + ", value=" + value + '}';
    }
}
