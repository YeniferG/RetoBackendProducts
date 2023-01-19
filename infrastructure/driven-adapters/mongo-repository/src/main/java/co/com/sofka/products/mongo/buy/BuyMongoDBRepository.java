package co.com.sofka.products.mongo.buy;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyMongoDBRepository extends ReactiveMongoRepository<BuyDocument/* change for adapter model */, String>, ReactiveQueryByExampleExecutor<BuyDocument/* change for adapter model */> {
}
