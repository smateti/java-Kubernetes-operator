package gov.nystax.nimbus.flink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkPrometheusIntegration {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String prometheusUrl = "http://localhost:9090";
        String query = "up";

        // Source: Pull data from Prometheus
        DataStream<String> prometheusStream = env.addSource(new PrometheusSourceFunction(prometheusUrl, query));

        // Process and Sink: Push results to Prometheus
        prometheusStream
            .map(new PrometheusSinkFunction()) // Register metrics
            .print();

        env.execute("Flink Prometheus Integration");
    }
}
