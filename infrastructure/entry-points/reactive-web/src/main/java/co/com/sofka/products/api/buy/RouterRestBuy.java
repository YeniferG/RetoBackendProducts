package co.com.sofka.products.api.buy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class RouterRestBuy {
@Bean
public RouterFunction<ServerResponse> routerFunctionBuy(HandlerBuy handler) {
    return route(POST("/api/buy"), handler::listenPostAddBuyUseCase)
            .andRoute(GET("/api/buys"), handler::listenFindAllBuysUseCase);
    }
}
