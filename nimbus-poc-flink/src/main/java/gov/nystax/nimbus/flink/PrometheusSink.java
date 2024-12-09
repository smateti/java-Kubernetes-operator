package gov.nystax.nimbus.flink;
import org.apache.flink.api.connector.sink.SinkWriter.Context;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

public class PrometheusSink implements SinkFunction<String> {
    private final String pushGatewayUrl;

    public PrometheusSink(String pushGatewayUrl) {
        this.pushGatewayUrl = pushGatewayUrl;
    }

    @Override
    public void invoke(String value, Context context) throws Exception {
        String body = "# HELP aggregated_metric Aggregated metric\n" +
                      "# TYPE aggregated_metric gauge\n" +
                      "aggregated_metric " + value;

        Request.Post(pushGatewayUrl + "/metrics/job/flink")
               .bodyString(body, ContentType.TEXT_PLAIN)
               .execute();
    }
}
