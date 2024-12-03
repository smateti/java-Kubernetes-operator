package gov.nystax.nimbus.react;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/error-demo")
    public Mono<String> demoErrorHandling() {
        return Mono.just("Success")
                   .map(value -> {
                       if (value.equals("Success")) {
                           throw new RuntimeException("Simulated Error");
                       }
                       return value;
                   })
                   .onErrorResume(e -> Mono.just("Fallback Value"));
    }

}
