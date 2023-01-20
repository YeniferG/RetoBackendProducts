package co.com.sofka.products.model.buy.valuesobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductsInfo {
    private String idProduct;
    private Integer quantity;
}
