package co.com.sofka.products.mongo.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocument {

    @Id
    private String id;
    private String name;
    private Integer inventory;
    private Boolean enabled;
    private Integer min;
    private Integer max;

}
