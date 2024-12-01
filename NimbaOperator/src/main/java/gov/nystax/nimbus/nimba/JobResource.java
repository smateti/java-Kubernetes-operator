
package gov.nystax.nimbus.nimba;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("example.com")
@Version("v1")
public class JobResource extends CustomResource<JobResourceSpec, Void> {
    // No status; use Void
}

class JobResourceSpec {
    private String image;
    private String[] command;

    // Getters and setters
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getCommand() {
        return command;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }
}
