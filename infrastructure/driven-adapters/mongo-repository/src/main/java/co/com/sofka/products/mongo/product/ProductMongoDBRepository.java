package co.com.sofka.products.mongo.product;

import co.com.sofka.products.model.product.Product;
import co.com.sofka.products.mongo.buy.BuyDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductMongoDBRepository extends ReactiveMongoRepository<ProductDocument/* change for adapter model */, String>, ReactiveQueryByExampleExecutor<ProductDocument/* change for adapter model */> {
}
