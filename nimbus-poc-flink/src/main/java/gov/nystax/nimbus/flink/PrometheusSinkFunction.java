package gov.nystax.nimbus.flink;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;

public class PrometheusSinkFunction extends RichMapFunction<String, String> {
    private transient Counter counter;

    @Override
    public void open(Configuration parameters) throws Exception {
        counter = getRuntimeContext().getMetricGroup().counter("processedRecords");
    }

    @Override
    public String map(String value) throws Exception {
        counter.inc();
        return value;
    }
}


