package in.francl.urbandollop.infrastructure.rdb.entity;

import in.francl.urbandollop.domain.model.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity(name = "Product") @Table(name = "products")
@Getter @AllArgsConstructor(access = AccessLevel.PRIVATE) @NoArgsConstructor
public class ProductEntity extends Product {
    
    @Id
    @GeneratedValue(generator = "gen-uuid")
    @GenericGenerator(name = "gen-uuid", strategy = "uuid")
    private String id;
    
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    
    public static ProductEntity of(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
    
    public static Product of(String id, String name, String description, BigDecimal price) {
        return new ProductEntity(
                id,
                name,
                description,
                price
        );
    }
    
}
