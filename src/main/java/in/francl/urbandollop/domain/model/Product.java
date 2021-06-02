package in.francl.urbandollop.domain.model;

import java.math.BigDecimal;


public abstract class Product {
    
    public abstract String getId();
    
    public abstract String getName();

    public abstract String getDescription();
    
    public abstract BigDecimal getPrice();
    
    public Product edit(Product product) {
        return ProductModel.of(
                this.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
    
}
