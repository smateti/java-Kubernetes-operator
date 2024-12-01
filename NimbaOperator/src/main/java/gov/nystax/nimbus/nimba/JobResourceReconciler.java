package gov.nystax.nimbus.nimba;


import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.*;


public class JobResourceReconciler implements Reconciler<JobResource> {

    private final KubernetesClient kubernetesClient;

    public JobResourceReconciler(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    @Override
    public UpdateControl<JobResource> reconcile(JobResource resource, Context context) {
        String namespace = resource.getMetadata().getNamespace();
        String name = resource.getMetadata().getName();
        String image = resource.getSpec().getImage();
        String[] command = resource.getSpec().getCommand();

        Job job = new JobBuilder()
                .withNewMetadata()
                    .withName(name)
                    .withNamespace(namespace)
                .endMetadata()
                .withNewSpec()
                    .withNewTemplate()
                        .withNewMetadata()
                            .addToLabels("app", name)
                        .endMetadata()
                        .withNewSpec()
                            .addNewContainer()
                                .withName("job-container")
                                .withImage(image)
                                .withCommand(command)
                            .endContainer()
                            .withRestartPolicy("Never")
                        .endSpec()
                    .endTemplate()
                .endSpec()
                .build();

        kubernetesClient.batch().v1().jobs().inNamespace(namespace).resource(job).serverSideApply();
        
        return UpdateControl.noUpdate();
    }
}
