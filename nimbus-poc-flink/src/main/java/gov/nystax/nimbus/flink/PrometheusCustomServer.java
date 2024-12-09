package gov.nystax.nimbus.flink;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.Gauge;

public class PrometheusCustomServer {
    public static final Gauge aggregatedMetric = Gauge.build()
            .name("aggregated_metric")
            .help("Aggregated metric exposed via HTTP")
            .register();

    public static void main(String[] args) throws Exception {
        HTTPServer server = new HTTPServer(8081);
        aggregatedMetric.set(123.45); // Example metric value
    }
}
