package gov.nystax.nimbus.react;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {
    @GetMapping("/test")
    public String loadTestPage() {
        return "test"; // Refers to templates/test.html
    }
}

