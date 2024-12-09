package gov.nystax.nimbus.flink;
public class PrometheusMetric {
    public String instance;
    public double value;

    public PrometheusMetric(String instance, double value) {
        this.instance = instance;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Instance: %s, Value: %f", instance, value);
    }
}
