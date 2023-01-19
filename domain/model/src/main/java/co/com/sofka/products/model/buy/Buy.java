package co.com.sofka.products.model.buy;
import co.com.sofka.products.model.buy.valuesobject.ProductsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Buy {

    private String id;
    private Date date;
    private String clientTypeDocument;
    private Integer clientIdentification;
    private String clientName;
    private List<ProductsInfo> products;
}
