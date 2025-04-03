package com.example.operator.cr;

public class ApplicationImagePatchSpec {
    private String deploymentName;
    private String containerName;
    private String newImage;

    // Getters and setters
    public String getDeploymentName() {
        return deploymentName;
    }
    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }
    public String getContainerName() {
        return containerName;
    }
    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
    public String getNewImage() {
        return newImage;
    }
    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }
}
