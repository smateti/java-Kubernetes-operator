package gov.nystax.nimbus.flink;


import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

public class FlinkPrometheusAggregation {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String prometheusUrl = "http://localhost:9090";
        String query = "http_requests_total"; // Example query

        // Source: Pull data from Prometheus
        DataStream<String> prometheusStream = env.addSource(new PrometheusSourceFunction(prometheusUrl, query));

        // Parse metrics
        DataStream<Metric> parsedMetrics = prometheusStream.map(new PrometheusParser());

        // Aggregate metrics hourly
        DataStream<String> aggregatedMetrics = parsedMetrics
            .keyBy(metric -> metric.instance) // Group by instance
            .timeWindow(Time.hours(1))        // Hourly tumbling window
            .aggregate(new MetricAggregator());

        // Sink: Write aggregated metrics to Prometheus
        aggregatedMetrics.addSink(new PrometheusSink("http://localhost:9091"));

        env.execute("Flink Prometheus Aggregation");
    }
}
