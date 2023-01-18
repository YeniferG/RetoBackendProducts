package co.com.sofka.products.usecase.product;

import co.com.sofka.products.model.product.Product;
import co.com.sofka.products.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductRepository productRepository;

    public Flux<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    public Mono<Product> addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteProductById(id);
    }

    public Mono<Product> updateProduct(String id, Product product) {
        return productRepository.updateProduct(id, product);
    }

}
