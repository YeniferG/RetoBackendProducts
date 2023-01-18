package co.com.sofka.products.api.product;

import co.com.sofka.products.model.product.Product;
import co.com.sofka.products.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerProduct {
//private  final UseCase useCase;
//private  final UseCase2 useCase2;
    private final ProductUseCase productUseCase;

    public Mono<ServerResponse> listenPostAddProductUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Product.class)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productUseCase.addProduct(product), Product.class));
    }

    public Mono<ServerResponse> listenFindAllProductsUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productUseCase.findAllProducts(), Product.class);
    }

    public Mono<ServerResponse> listenUpdateProductUseCase(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(Product.class)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productUseCase.updateProduct(id, product),
                                Product.class));
    }

    public Mono<ServerResponse> listenDeleteProductUseCase(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productUseCase.deleteProduct(id), Product.class);
    }
}
