package co.com.sofka.products.api.buy;

import co.com.sofka.products.model.buy.Buy;
import co.com.sofka.products.model.product.Product;
import co.com.sofka.products.usecase.buy.BuyUseCase;
import co.com.sofka.products.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerBuy {
    private final BuyUseCase buyUseCase;

    public Mono<ServerResponse> listenPostAddBuyUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Buy.class).flatMap(buy ->
                buyUseCase.saveBuy(buy).flatMap(success -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(success))
                        .onErrorResume(error -> Mono.just(error.getMessage()).flatMap(s -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).bodyValue(s))));
    }

    public Mono<ServerResponse> listenFindAllBuysUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buyUseCase.findAllBuy(), Buy.class);
    }
}
