import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.javaoperatorsdk.operator.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatorApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Java Kubernetes Operator...");
        
        try (KubernetesClient client = new KubernetesClientBuilder().build()) {
            Operator operator = new Operator(client);
            operator.register(new DeploymentImagePatchReconciler(client));

            LOGGER.info("Operator is running...");
            operator.start();
        } catch (Exception e) {
            LOGGER.error("Failed to start operator", e);
        }
    }
}
