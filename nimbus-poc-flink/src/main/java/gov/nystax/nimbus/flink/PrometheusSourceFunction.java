package gov.nystax.nimbus.flink;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

public class PrometheusSourceFunction implements SourceFunction<String> {

    private volatile boolean isRunning = true;
    private final String prometheusUrl;
    private final String query;

    public PrometheusSourceFunction(String prometheusUrl, String query) {
        this.prometheusUrl = prometheusUrl;
        this.query = query;
    }

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (isRunning) {
            try {
                // Build Prometheus API request
                String url = String.format("%s/api/v1/query?query=%s", prometheusUrl, query);
                Response response = Request.Get(url).execute();
                String jsonResponse = response.returnContent().asString();

                // Emit response as a string (can be parsed as JSON if needed)
                ctx.collect(jsonResponse);

                // Poll every 10 seconds
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
