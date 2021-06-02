package in.francl.urbandollop.domain.repository;

import in.francl.urbandollop.domain.model.Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;


public interface ProductRepository {

    Optional<? extends Product> findProductById(String id);
    
    Product saveOrUpdate(Product product);
    
    Collection<? extends Product> retrieveAll();
    
    Collection<? extends Product> search(String q, BigDecimal minPrice, BigDecimal maxPrice);
    
    void deleteProduct(String id);

}
