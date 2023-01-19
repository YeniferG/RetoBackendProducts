package co.com.sofka.products.mongo.product;

import co.com.sofka.products.model.product.Product;
import co.com.sofka.products.model.product.gateways.ProductRepository;
import co.com.sofka.products.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductMongoRepositoryAdapter extends AdapterOperations<Product/* change for domain model */, ProductDocument/* change for adapter model */, String, ProductMongoDBRepository>
implements ProductRepository
{

    public ProductMongoRepositoryAdapter(ProductMongoDBRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Product.class/* change for domain model */));
    }

    @Override
    public Mono<Product> addProduct(Product product) {
        return repository.save(new ProductDocument(product.getId(), product.getName(), product.getInventory(), product.getEnabled(), product.getMax(), product.getMin())).flatMap(element -> {
            product.setId(element.getId());
            return Mono.just(product);
        });
    }

    @Override
    public Flux<Product> findAllProducts() {
        return repository.findAll().flatMap(
                element -> Flux.just(new Product(element.getId(), element.getName(), element.getInventory(), element.getEnabled(), element.getMin(), element.getMax())
                ));
    }

    @Override
    public Mono<Void> deleteProductById(String id) {
        return null;
    }

    @Override
    public Mono<Product> updateProduct(String id, Product product) {
        return repository.save(
                new ProductDocument(
                        id,
                        product.getName(),
                        product.getInventory(),
                        product.getEnabled(),
                        product.getMax(),
                        product.getMin()
                )
        ).flatMap(element -> Mono.just(product));
    }

    @Override
    public Mono<Product> findById(String id) {
        return repository.findById(id).flatMap(element -> Mono.just(new Product(element.getId(), element.getName(), element.getInventory(), element.getEnabled(), element.getMin(), element.getMax())));
    }
}
