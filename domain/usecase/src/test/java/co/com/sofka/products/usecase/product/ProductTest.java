package co.com.sofka.products.usecase.product;

import co.com.sofka.products.model.product.Product;
import co.com.sofka.products.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @InjectMocks
    ProductUseCase productUseCase;

    @Mock
    ProductRepository productRepository;

    @Test
    void findAllProductsTest() {
        // ARR
        Product product = new Product("123", "Pablo", 50, true, 1, 45 );
        Product product2 = new Product("456", "Juan", 100, true, 1, 45 );


        Flux<Product> productFlux = Flux.fromIterable(List.of(product, product2));
        when(productRepository.findAllProducts()).thenReturn(productFlux);

        //ACT
        StepVerifier.create(productUseCase.findAllProducts())
                .expectNextMatches(p1 -> p1.getId().equals(product.getId()))
                .expectNextMatches(p2 -> p2.getId().equals(product2.getId()))
                .verifyComplete();
    }

    @Test
    void addProductTest() {
        // ARR
        Product product = new Product("123", "Pablo", 50, true, 1, 45 );

        Mono<Product> productMono = Mono.just(product);
        when(productRepository.addProduct(product)).thenReturn(productMono);

        //ACT
        StepVerifier.create(productUseCase.addProduct(product)) //ASSERT
                .expectNextMatches(product1 -> product1.getId().equals(product1.getId()))
                .verifyComplete();
    }

    @Test
    void existProduct() {
        // ARR
        Mono<Boolean> productMono = Mono.just(true);
        when(productRepository.existsById("123")).thenReturn(productMono);

        Assertions.assertTrue(productUseCase.existsById("123"));
    }

    @Test
    void findByIdProductTest() {
        // ARR
        Product product = new Product("123", "Mario", 50, true, 1, 45 );

        Mono<Product> productMono = Mono.just(product);
        when(productRepository.findById("123")).thenReturn(productMono);

        //ACT
        StepVerifier.create(productUseCase.findProductById("123"))
                .expectNextMatches(product1 -> product1.getId().equals(product.getId()) && product1.getName().equals(product.getName()))
                .verifyComplete();
    }

    @Test
    void deleteByIdProductTest() {
        // ARR
        Product product = new Product("123", "Yeni", 50, true, 1, 45 );

        Mono<Void> voidMono = Mono.empty();
        when(productRepository.deleteById("123")).thenReturn(voidMono);

        //ACT
        StepVerifier.create(productUseCase.deleteProduct("123"))
                .expectNextMatches(response -> false)
                .expectNext()
                .expectComplete();
    }

    @Test
    void upadteProductTest() {

        Product product = new Product("123", "Julian", 50, true, 1, 45 );

        Mono<Product> productMono = Mono.just(product);
        when(productRepository.updateProduct("123", product)).thenReturn(productMono);

        //ACT
        StepVerifier.create(productUseCase.updateProduct("123",product))
                .expectNextMatches(product1 -> product1.getId().equals(product.getId()))
                .verifyComplete();
    }
}
