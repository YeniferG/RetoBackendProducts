package co.com.sofka.products.api.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class RouterRestProduct {
@Bean
public RouterFunction<ServerResponse> routerFunctionProduct(HandlerProduct handler) {
    return route(POST("/api/product"), handler::listenPostAddProductUseCase)
            .andRoute(GET("/api/products"), handler::listenFindAllProductsUseCase)
            .andRoute(PUT("/api/product/{id}"), handler::listenUpdateProductUseCase)
            .andRoute(DELETE("/api/product/{id}"), handler::listenDeleteProductUseCase);

    }
}
