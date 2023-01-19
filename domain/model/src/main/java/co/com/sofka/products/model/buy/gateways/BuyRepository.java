package co.com.sofka.products.model.buy.gateways;

import co.com.sofka.products.model.buy.Buy;
import co.com.sofka.products.model.product.Product;
import reactor.core.publisher.Mono;

public interface BuyRepository {

    Mono<Buy> saveBuy(Buy buy);

    Mono<Boolean> productAvailability(Buy buy);

    Mono<Boolean> productQuantities(String id);

    Mono<Void> deductProductQuantities(String id, String quantity);

}
