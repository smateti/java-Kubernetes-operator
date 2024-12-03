package gov.nystax.nimbus.react;

import reactor.core.publisher.Flux;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private static final List<User> users = List.of(
            new User("1", "Alice"),
            new User("2", "Bob"),
            new User("3", "Charlie")
    );

    public Flux<User> findAll() {
        return Flux.fromIterable(users);
    }
}

