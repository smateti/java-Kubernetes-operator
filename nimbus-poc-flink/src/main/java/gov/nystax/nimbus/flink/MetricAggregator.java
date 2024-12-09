package gov.nystax.nimbus.flink;

import org.apache.flink.api.common.functions.AggregateFunction;

public class MetricAggregator implements AggregateFunction<Metric, MetricAccumulator, String> {

    @Override
    public MetricAccumulator createAccumulator() {
        return new MetricAccumulator();
    }

    @Override
    public MetricAccumulator add(Metric value, MetricAccumulator accumulator) {
        accumulator.total += value.value;
        accumulator.count++;
        return accumulator;
    }

    @Override
    public String getResult(MetricAccumulator accumulator) {
        double average = accumulator.count == 0 ? 0 : accumulator.total / accumulator.count;
        return String.format("Instance: %s, Total: %.2f, Average: %.2f",
                accumulator.instance, accumulator.total, average);
    }

    @Override
    public MetricAccumulator merge(MetricAccumulator a, MetricAccumulator b) {
        a.total += b.total;
        a.count += b.count;
        return a;
    }
}

class MetricAccumulator {
    String instance;
    double total = 0;
    long count = 0;
}
