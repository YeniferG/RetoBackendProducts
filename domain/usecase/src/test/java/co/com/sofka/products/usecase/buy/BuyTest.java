package co.com.sofka.products.usecase.buy;

import co.com.sofka.products.model.buy.Buy;
import co.com.sofka.products.model.buy.gateways.BuyRepository;
import co.com.sofka.products.model.buy.valuesobject.ProductsInfo;
import co.com.sofka.products.model.common.ex.BusinessException;
import co.com.sofka.products.model.product.Product;
import co.com.sofka.products.usecase.product.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuyTest {

    @InjectMocks
    BuyUseCase buyUseCase;
    @Mock
    BuyRepository buyRepository;
    @Mock
    ProductUseCase productUseCase;

    private Buy buyWithNullFields;
    private Buy buyWithProductsEmpty;
    private Buy orderWithLimits;
    private Buy goodBuy;
    private Buy goodBuy2;
    private Product productWithLimits1;
    private Product goodProduct;
    private Product productWithDiscount;
    private ProductsInfo productsInfoWithLimits1;


    private final String TEST_STRING = "TEST";

    private final Integer TEST_INTEGER = 100;
    private final Long TEST_LONG = 1L;

    @BeforeEach
    void setup(){
        buyWithNullFields = Buy.builder()
                .id(null)
                .date(null)
                .clientTypeDocument(null)
                .clientIdentification(null)
                .clientName(null)
                .products(null)
                .build();

        buyWithProductsEmpty = Buy.builder()
                .id(TEST_STRING)
                .date(LocalDateTime.now())
                .clientTypeDocument(TEST_STRING)
                .clientIdentification(TEST_LONG)
                .clientName(TEST_STRING)
                .products(Collections.EMPTY_LIST)
                .build();

        productsInfoWithLimits1 = ProductsInfo
                .builder()
                .idProduct(TEST_STRING)
                .quantity(10)
                .build();


        productWithLimits1 = Product
                .builder()
                .id(TEST_STRING)
                .name(TEST_STRING)
                .inventory(TEST_INTEGER)
                .enabled(true)
                .min(11)
                .max(30)
                .build();

        goodProduct = Product
                .builder()
                .id(TEST_STRING)
                .name(TEST_STRING)
                .inventory(TEST_INTEGER)
                .enabled(true)
                .min(5)
                .max(100)
                .build();

        productWithDiscount = Product
                .builder()
                .id(TEST_STRING)
                .name(TEST_STRING)
                .inventory(50)
                .enabled(true)
                .min(5)
                .max(100)
                .build();


        orderWithLimits = Buy.builder()
                .id(TEST_STRING)
                .date(LocalDateTime.now())
                .clientTypeDocument(TEST_STRING)
                .clientIdentification(TEST_LONG)
                .clientName(TEST_STRING)
                .products(List.of(productsInfoWithLimits1))
                .build();


        goodBuy = Buy.builder()
                .id(TEST_STRING)
                .date(LocalDateTime.now())
                .clientTypeDocument(TEST_STRING)
                .clientIdentification(TEST_LONG)
                .clientName(TEST_STRING)
                .products(Collections.singletonList(ProductsInfo
                        .builder()
                        .idProduct(TEST_STRING)
                        .quantity(50)
                        .build()))
                .build();

        goodBuy2 = Buy.builder()
                .id("123")
                .date(LocalDateTime.now())
                .clientTypeDocument(TEST_STRING)
                .clientIdentification(TEST_LONG)
                .clientName(TEST_STRING)
                .products(Collections.singletonList(ProductsInfo
                        .builder()
                        .idProduct(TEST_STRING)
                        .quantity(50)
                        .build()))
                .build();
    }

    @Test
    void buyShouldNotBeSavedBecauseHasNullFields(){

        Mono<Buy> result = buyUseCase.saveBuy(buyWithNullFields);

        StepVerifier.create(result)
                .verifyErrorSatisfies(throwable -> assertThat(throwable)
                        .isInstanceOf(BusinessException.class));
    }

    @Test
    void buyShouldNotBeSavedBecauseHasProductsWithNullFields(){

        Mono<Buy> result = buyUseCase.saveBuy(buyWithProductsEmpty);

        StepVerifier.create(result)
                .verifyErrorSatisfies(throwable -> assertThat(throwable)
                        .isInstanceOf(BusinessException.class));
    }

    @Test
    void buyShouldNotBeSavedBecauseExceedsLimits(){



        Mono<Buy> result = buyUseCase.saveBuy(orderWithLimits);

        StepVerifier.create(result)
                .verifyErrorSatisfies(throwable -> assertThat(throwable)
                        .isInstanceOf(BusinessException.class));


    }

    @Test
    void buyShouldBeSaved(){

        when(productUseCase.findProductById(TEST_STRING)).thenReturn(Mono.just(goodProduct));
        when(productUseCase.existsById(TEST_STRING)).thenReturn(true);
        when(productUseCase.updateProduct(productWithDiscount.getId(), productWithDiscount)).thenReturn(Mono.just(productWithDiscount));
        when(buyRepository.saveBuy(goodBuy)).thenReturn(Mono.just(goodBuy));

        Mono<Buy> result = buyUseCase.saveBuy(goodBuy);

        StepVerifier.create(result)
                .expectNextMatches(b1 -> b1.getId().equals(goodBuy.getId()))
                .verifyComplete();
    }

    @Test
    void findAllBuyTest() {

        Flux<Buy> buyFlux = Flux.fromIterable(List.of(goodBuy, goodBuy2));
        when(buyRepository.findAllBuy()).thenReturn(buyFlux);

        //ACT
        StepVerifier.create(buyUseCase.findAllBuy())
                .expectNextMatches(b1 -> b1.getId().equals(goodBuy.getId()))
                .expectNextMatches(b2 -> b2.getId().equals(goodBuy2.getId()))
                .verifyComplete();
    }

}
