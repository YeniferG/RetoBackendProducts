package co.com.sofka.products.usecase.buy;

import co.com.sofka.products.model.buy.Buy;
import co.com.sofka.products.model.buy.gateways.BuyRepository;
import co.com.sofka.products.model.buy.valuesobject.ProductsInfo;
import co.com.sofka.products.model.common.ex.BusinessException;
import co.com.sofka.products.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class BuyUseCase {

    private final BuyRepository buyRepository;
    private final ProductUseCase productUseCase;

    public Mono<Buy> saveBuy(Buy buy) {

        return Mono.just(Arrays.asList(buy.getId(), buy.getClientTypeDocument(), buy.getClientIdentification(), buy.getClientName())
                                .stream()
                                .filter(field -> isNull(field))
                                .collect(Collectors.toList()))
                                .flatMap(list -> !validateList(list)
                                ? validateNullabilityBuyProductsInfo(buy)
                                : Mono.error(BusinessException.Type.BUY_WITH_NULL_FIELDS.build("")));
    }

    private static boolean validateList(List<? extends Serializable> list) {
        return !list.isEmpty() || list.stream().anyMatch(serializable -> isNull(serializable));
    }

    private Mono<Buy> validateNullabilityBuyProductsInfo(Buy buy) {
        return Mono.just(buy)
                .flatMap(buy1 -> validateNullabilityBuy(buy, buy1));
    }

    private Mono<Buy> validateNullabilityBuy(Buy buy, Buy buy1) {
        return validateNullabilityBuyProductsInfoPredicate(buy, buy1)
                ? Mono.error(BusinessException.Type.BUY_WITH_NULL_OR_EMPTY_PRODUCTS.build(""))
                : Mono.just(buy1)
                .map(buy2 -> buildBuyWithLocalDate(buy2))
                .flatMap(buy2 -> useProductsInfoList(buy2));
    }

    private Mono<Buy> useProductsInfoList(Buy buy2) {
        List<ProductsInfo> productsInfoList = getProductsInfoList(buy2);
        buildBuyWithProductsInfoList(buy2, productsInfoList);
        return Mono.just(buildBuyWithProductsInfoList(buy2, productsInfoList))
                .flatMap(buy -> getUseProductsInfoListOperations(buy));
    }

    private Mono<Buy> getUseProductsInfoListOperations(Buy buy) {

        return Flux.fromIterable(buy.getProducts())
                .flatMap(productsInfo -> productUseCase.findProductById(productsInfo.getIdProduct())
                        .flatMap(product -> (productsInfo.getQuantity()
                                >= product.getMin()) && (productsInfo.getQuantity()
                                <= product.getMax()) ? Mono.just(productsInfo) : Mono.error(BusinessException.Type.EXCEDS_PRODUCTS.build(""))))
                .collectList()
                .flatMap(products -> products.isEmpty() ?
                        Mono.error(BusinessException.Type.BUY_WITH_NULL_OR_EMPTY_PRODUCTS.build(""))
                        : updateBuyProducts(buy, products))
                .flatMap(buy1 -> validateBuyProductsSize(buy, buy1));
    }

    private Mono<Buy> validateBuyProductsSize(Buy buy, Buy buy1) {
        return !isNull(buy1.getProducts()) || !buy1.getProducts().isEmpty() ?
                deductProductQuantities(buy)
                : Mono.error(BusinessException.Type.BUY_WITH_NULL_OR_EMPTY_PRODUCTS.build(""));
    }

    private Mono<Buy> updateBuyProducts(Buy buy, List<ProductsInfo> products) {
        return Mono.just(buy.toBuilder()
                .products(products)
                .build());
    }

    private Buy buildBuyWithProductsInfoList(Buy buy2, List<ProductsInfo> productsInfoList) {
        return Buy.builder()
                .id(buy2.getId())
                .date(buy2.getDate())
                .clientTypeDocument(buy2.getClientTypeDocument())
                .clientIdentification(buy2.getClientIdentification())
                .clientName(buy2.getClientName())
                .products(productsInfoList)
                .build();
    }

    private List<ProductsInfo> getProductsInfoList(Buy buy2) {
        return buy2.getProducts().stream()
                .filter(productsInfo -> productUseCase.existsById(productsInfo.getIdProduct()))
                .collect(Collectors.toList());
    }

    private boolean validateNullabilityBuyProductsInfoPredicate(Buy buy, Buy buy1) {
        return buy1.getProducts().isEmpty() || !getNullProductsInfo(buy).isEmpty();
    }

    private List<ProductsInfo> getNullProductsInfo(Buy buy) {
        return buy.getProducts().stream().filter(productsInfo -> isNull(productsInfo)).collect(Collectors.toList());
    }

    private Buy buildBuyWithLocalDate(Buy buy2) {
        return Buy.builder()
                .id(buy2.getId())
                .date(buy2.getDate() == null ? LocalDateTime.now() : buy2.getDate())
                .clientTypeDocument(buy2.getClientTypeDocument())
                .clientIdentification(buy2.getClientIdentification())
                .clientName(buy2.getClientName())
                .products(buy2.getProducts()).build();
    }

    private Mono<Buy> deductProductQuantities(Buy buy) {
        return Flux.fromIterable(buy.getProducts()).flatMap(product ->
                    productUseCase.findProductById(product.getIdProduct())
                            .map(product1 -> product1.toBuilder()
                                    .inventory(product1.getInventory() - product.getQuantity())
                                    .build())
                                    .flatMap(product1 -> productUseCase.updateProduct(product1.getId(), product1)))
                .collectList()
                .flatMap(products -> buyRepository.saveBuy(buy));
    }

    public Flux<Buy> findAllBuy() {
        return buyRepository.findAllBuy();
    }
}