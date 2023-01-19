package co.com.sofka.products.usecase.buy;

import co.com.sofka.products.model.buy.Buy;
import co.com.sofka.products.model.buy.gateways.BuyRepository;
import co.com.sofka.products.model.buy.valuesobject.ProductsInfo;
import co.com.sofka.products.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BuyUseCase {

    private final BuyRepository buyRepository;
    private final ProductUseCase productUseCase;

    public Mono<Buy> saveBuy(Buy buy) {

        buy.setDate(LocalDateTime.now()); //Aqui se asigna la fecha actual segun el servidor

        buy.setProducts(productAvailability(buy)); //Aqui se consulta disponibilidad del producto

        if (productQuantities(buy)) {
            return Mono.error(new IllegalArgumentException("Error, cantidad del producto fuera de los rangos minimos y maximos del inventario"));
        }
        ; //Aqui se evaluan las cantidades de los productos

        deductProductQuantities(buy); //Aqui se descuenta del inventario las cantidades compradas

        return buyRepository.saveBuy(buy);
    }


    public Flux<Buy> findAllBuy() {
        return buyRepository.findAllBuy();
    }

    public List<ProductsInfo> productAvailability(Buy buy) {
        List<ProductsInfo> listProducts = buy.getProducts().stream().filter(product -> productUseCase.existsById(product.getIdProduct())).collect(Collectors.toList());

        return listProducts;
    }

    public boolean productQuantities(Buy buy) {
        List<Boolean> listProducts = buy.getProducts().stream().map(product -> {
            var productInventory = productUseCase.findProductById(product.getIdProduct()).toFuture().join();
            if (product.getQuantity() >= productInventory.getMin() && product.getQuantity() <= productInventory.getMax()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        return listProducts.contains(false);
    }

    public void deductProductQuantities(Buy buy) {
        buy.getProducts().forEach(product -> {
            productUseCase.findProductById(product.getIdProduct()).map(product1 -> {
                /*var newProductInventory = product1.getInventory() - product.getQuantity();
                            product1.setInventory(newProductInventory);
                            Logger.getLogger("Yenifer").info("Actualizando producto" + product1.getInventory());*/
                product1.setName("prueba");
                        return productUseCase.updateProduct(product1.getId(), product1);
                    }
            );

        });
    }
}