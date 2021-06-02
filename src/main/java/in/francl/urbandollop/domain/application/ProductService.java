package in.francl.urbandollop.domain.application;

import in.francl.urbandollop.domain.datatransfer.product.ProductRequest;
import in.francl.urbandollop.domain.datatransfer.product.ProductResponse;

import java.math.BigDecimal;
import java.util.Collection;

public interface ProductService {
    
    ProductResponse create(ProductRequest product);
    
    ProductResponse update(String id, ProductRequest product);
    
    ProductResponse findOneById(String id);
    
    Collection<ProductResponse> retrieveAll();
    
    Collection<ProductResponse> search(String q, BigDecimal minPrice, BigDecimal maxPrice);
    
    void delete(String id);
    
}
