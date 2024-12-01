package gov.nystax.nimbus.nimba;



import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.javaoperatorsdk.operator.Operator;

public class OperatorMain {
    public static void main(String[] args) {
        KubernetesClient client = new KubernetesClientBuilder().build();

        Operator operator = new Operator(client);
        operator.register(new JobResourceReconciler(client));

        operator.start();
    }
}
