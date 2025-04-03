import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.fabric8.kubernetes.api.model.apps.DeploymentStatus;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.javaoperatorsdk.operator.api.reconciler.*;
import io.javaoperatorsdk.operator.api.reconciler.dependent.kubernetes.PatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ControllerConfiguration
public class DeploymentImagePatchReconciler implements Reconciler<Deployment> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentImagePatchReconciler.class);
    private final KubernetesClient client;

    public DeploymentImagePatchReconciler(KubernetesClient client) {
        this.client = client;
    }

    @Override
    public UpdateControl<Deployment> reconcile(Deployment deployment, Context<Deployment> context) {
        String namespace = deployment.getMetadata().getNamespace();
        String deploymentName = deployment.getMetadata().getName();

        LOGGER.info("Reconciling deployment: {}", deploymentName);

        // Retrieve current image
        List<io.fabric8.kubernetes.api.model.Container> containers = deployment.getSpec().getTemplate().getSpec().getContainers();
        if (containers.isEmpty()) {
            LOGGER.warn("No containers found in Deployment: {}", deploymentName);
            return UpdateControl.noUpdate();
        }

        // Patch Image ID (Example: Change to "myrepo/myapp:latest")
        String newImageId = "myrepo/myapp:latest";
        containers.get(0).setImage(newImageId); // Modify first container

        // Patch the deployment
        try {
            client.apps().deployments()
                .inNamespace(namespace)
                .withName(deploymentName)
                .patch(PatchType.STRATEGIC_MERGE, deployment);

            LOGGER.info("Updated Deployment {} image to {}", deploymentName, newImageId);
        } catch (KubernetesClientException e) {
            LOGGER.error("Failed to patch Deployment {}", deploymentName, e);
            return UpdateControl.noUpdate();
        }

        return UpdateControl.noUpdate(); // No need to requeue
    }
}
