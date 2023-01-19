package co.com.sofka.products.mongo.buy;

import co.com.sofka.products.model.buy.valuesobject.ProductsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BuyDocument {

    @Id
    private String id;
    private LocalDateTime date;
    private String clientTypeDocument;
    private Long clientIdentification;
    private String clientName;
    private List<ProductsInfo> products;

}
