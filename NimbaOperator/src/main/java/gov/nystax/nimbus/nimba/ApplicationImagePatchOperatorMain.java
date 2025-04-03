package com.example.operator;

import com.example.operator.reconciler.ApplicationImagePatchReconciler;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.Operator;

public class ApplicationImagePatchOperatorMain {
    public static void main(String[] args) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Operator operator = new Operator(client);
            operator.register(new ApplicationImagePatchReconciler(client));
            operator.start();
            // Keep the operator running (this example uses a simple sleep).
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
