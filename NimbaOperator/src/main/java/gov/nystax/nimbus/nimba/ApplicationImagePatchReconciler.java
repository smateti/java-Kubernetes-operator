package com.example.operator.reconciler;

import com.example.operator.cr.ApplicationImagePatch;
import com.example.operator.cr.ApplicationImagePatchSpec;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

@ControllerConfiguration
public class ApplicationImagePatchReconciler implements Reconciler<ApplicationImagePatch> {

    private final KubernetesClient client;

    public ApplicationImagePatchReconciler(KubernetesClient client) {
        this.client = client;
    }

    @Override
    public UpdateControl<ApplicationImagePatch> reconcile(ApplicationImagePatch resource, Context context) {
        String namespace = resource.getMetadata().getNamespace();
        ApplicationImagePatchSpec spec = resource.getSpec();
        String deploymentName = spec.getDeploymentName();
        String containerName = spec.getContainerName();
        String newImage = spec.getNewImage();

        Deployment deployment = client.apps()
                                      .deployments()
                                      .inNamespace(namespace)
                                      .withName(deploymentName)
                                      .get();

        if (deployment != null) {
            // Create a patched deployment using the builder.
            Deployment patchedDeployment = new DeploymentBuilder(deployment)
                    .editSpec()
                        .editTemplate()
                            .editSpec()
                                .editMatchingContainer(c -> c.getName().equals(containerName))
                                    .withImage(newImage)
                                .endContainer()
                            .endSpec()
                        .endTemplate()
                    .endSpec()
                    .build();

            // Patch the deployment.
            client.apps()
                  .deployments()
                  .inNamespace(namespace)
                  .withName(deploymentName)
                  .patch(patchedDeployment);

            System.out.println("Patched deployment '" + deploymentName +
                               "' in namespace '" + namespace +
                               "' with new image: " + newImage);
        } else {
            System.out.println("Deployment '" + deploymentName + "' not found in namespace '" + namespace + "'.");
        }

        // No changes to the custom resource status are made.
        return UpdateControl.noUpdate();
    }
}
