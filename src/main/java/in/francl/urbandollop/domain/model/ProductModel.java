package in.francl.urbandollop.domain.model;

import lombok.Getter;

import java.math.BigDecimal;


@Getter
public final class ProductModel extends Product {
    
    private final String id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    
    
    private ProductModel(String id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    public static Product of(String id, String name, String description, BigDecimal price) {
        return new ProductModel(
            id,
            name,
            description,
            price
        );
    }
    
    public static Product of(String name, String description, BigDecimal price) {
        return new ProductModel(
            null,
            name,
            description,
            price
        );
    }
    
    public static Product of(Product product) {
        return new ProductModel(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

}
