package co.com.sofka.products.usecase.buy;

import co.com.sofka.products.model.buy.Buy;
import co.com.sofka.products.model.buy.gateways.BuyRepository;
import co.com.sofka.products.model.product.Product;
import co.com.sofka.products.model.product.gateways.ProductRepository;
import co.com.sofka.products.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BuyUseCase {

    private final BuyRepository buyRepository;
    private final ProductUseCase productUseCase;

    public Mono<Buy> saveBuy(Buy buy) {
        //return buyRepository.saveBuy(buy);
        /**buy.getProducts().forEach(product -> {
            if (productUseCase.existProduct(buy.getId())) {
                Mono<Product> productModel = productRepository.findById(product.getIdProduct());
                if (product.getQuantity() > productModel.map(product1 -> product1.getMin()).block()
                        && product.getQuantity() < productModel.map(product1 -> product1.getMax()).block()) {
                    productModel.map(pm -> pm.setInventory(productModel.get().getInInventrory() - buy.getProducts().get(i).getQuantity()));
                    return new ResponseEntity<>(buyRepository.save(buy),HttpStatus.OK);
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);**/
    }

    public Mono<Boolean> productAvailability(Buy buy){
        buy.getProducts().stream().map(product -> {
            if (productUseCase.existProduct(buy.getId())) {
                return true;
            }
            return false;
        });
    }



}
