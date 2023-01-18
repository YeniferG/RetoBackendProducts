package co.com.sofka.products.model.buy.gateways;

import co.com.sofka.products.model.buy.Buy;
import co.com.sofka.products.model.product.Product;
import reactor.core.publisher.Mono;

public interface BuyRepository {

    Mono<Buy> addBuy(Buy buy);

    Mono<Buy> settleBuy(Buy buy);

    Mono<Void> deductProductQuantities(String id, String quantity);

    Mono<Product> productAvailability(String id);

}
