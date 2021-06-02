package in.francl.urbandollop.domain.datatransfer.product;

import in.francl.urbandollop.domain.model.Product;

import java.math.BigDecimal;


public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price
) {
    
    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
    
}
