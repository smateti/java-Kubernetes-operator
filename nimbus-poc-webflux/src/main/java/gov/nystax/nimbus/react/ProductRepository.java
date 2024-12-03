package gov.nystax.nimbus.react;

import reactor.core.publisher.Mono;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    public Mono<Product> findById(String id) {
        // Simulating database fetch
        if ("1".equals(id)) {
            return Mono.just(new Product("1", "Laptop"));
        } else {
            return Mono.empty(); // Return an empty Mono if not found
        }
    }
}
