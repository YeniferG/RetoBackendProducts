package co.com.sofka.products.model.product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;
    private String name;
    private Integer inventory;
    private Boolean enabled;
    private Integer min;
    private Integer max;
}
