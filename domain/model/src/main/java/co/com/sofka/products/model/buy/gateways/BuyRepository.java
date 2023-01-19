package co.com.sofka.products.model.buy.gateways;

import co.com.sofka.products.model.buy.Buy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BuyRepository {

    Mono<Buy> saveBuy(Buy buy);

    Flux<Buy> findAllBuy();

    Mono<Boolean> productAvailability(Buy buy);

    Mono<Boolean> productQuantities(String id);

    Mono<Void> deductProductQuantities(String id, String quantity);

}
