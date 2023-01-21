package co.com.sofka.products.model.product.gateways;

import co.com.sofka.products.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> addProduct(Product product);

    Flux<Product> findAllProducts();

    Mono<Void> deleteById(String id);

    Mono<Product> updateProduct(String id, Product product);

    Mono<Product> findById(String id);

    Mono<Boolean> existsById(String id);
}
