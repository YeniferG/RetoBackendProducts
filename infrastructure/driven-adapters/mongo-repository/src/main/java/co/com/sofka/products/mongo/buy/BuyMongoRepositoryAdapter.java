package co.com.sofka.products.mongo.buy;

import co.com.sofka.products.model.buy.Buy;
import co.com.sofka.products.model.buy.gateways.BuyRepository;
import co.com.sofka.products.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BuyMongoRepositoryAdapter extends AdapterOperations<Buy/* change for domain model */, BuyDocument/* change for adapter model */, String, BuyMongoDBRepository> implements BuyRepository {

    public BuyMongoRepositoryAdapter(BuyMongoDBRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Buy.class/* change for domain model */));
    }

    @Override
    public Mono<Buy> saveBuy(Buy buy) {
        return repository.save(
                        new BuyDocument(
                                buy.getId(),
                                buy.getDate(),
                                buy.getClientTypeDocument(),
                                buy.getClientIdentification(),
                                buy.getClientName(),
                                buy.getProducts()))
                .flatMap(element -> {
                    buy.setId(element.getId());
                    return Mono.just(buy);
                });
    }

    @Override
    public Flux<Buy> findAllBuy() {
        return repository.findAll().flatMap(
                buy -> Flux.just(
                        new Buy(buy.getId(),
                                buy.getDate(),
                                buy.getClientTypeDocument(),
                                buy.getClientIdentification(),
                                buy.getClientName(),
                                buy.getProducts()
                        )));
    }

    @Override
    public Mono<Boolean> productAvailability(Buy buy) {
        return null;
    }

    @Override
    public Mono<Boolean> productQuantities(String id) {
        return null;
    }

    @Override
    public Mono<Void> deductProductQuantities(String id, String quantity) {
        return null;
    }
}