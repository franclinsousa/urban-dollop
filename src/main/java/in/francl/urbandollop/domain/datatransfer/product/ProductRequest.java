package in.francl.urbandollop.domain.datatransfer.product;


import in.francl.urbandollop.domain.model.Product;
import in.francl.urbandollop.domain.model.ProductModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


public record ProductRequest(
        @NotBlank
        @Size(max = 256)
        String name,
        @NotBlank
        @Size(max = 4000)
        String description,
        @Min(0)
        BigDecimal price
) {
    
    public Product toProduct() {
        return ProductModel.of(
                this.name(),
                this.description(),
                this.price()
        );
    }
    
    public static ProductRequest of(Product product) {
        return new ProductRequest(
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
    
}
