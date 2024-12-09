package gov.nystax.nimbus.flink;
import org.apache.flink.api.common.functions.MapFunction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrometheusParser implements MapFunction<String, Metric> {
    @Override
    public Metric map(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(json);
        JsonNode result = root.path("data").path("result").get(0);

        String instance = result.path("metric").path("instance").asText();
        double value = result.path("value").get(1).asDouble();
        long timestamp = result.path("value").get(0).asLong() * 1000;

        return new Metric(instance, timestamp, value);
    }
}
