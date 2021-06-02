package in.francl.urbandollop.infrastructure.rdb.repository;

import in.francl.urbandollop.domain.model.Product;
import in.francl.urbandollop.domain.repository.ProductRepository;
import in.francl.urbandollop.infrastructure.rdb.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;


@Repository
public interface ProductRepositoryOnRdb extends JpaRepository<ProductEntity, String>, ProductRepository {
    
    @Override
    default Optional<? extends Product> findProductById(String id) {
        return findById(id);
    }
    
    @Override
    default Collection<? extends Product> retrieveAll() {
        return findAll();
    }
    
    @Override
    default Product saveOrUpdate(Product product) {
        return save(ProductEntity.of(product));
    }
    
    @Override
    default void deleteProduct(String id) {
        deleteById(id);
    }
    
    @Override
    @Query(value = """
            select p from Product p where
            (p.name like concat('%', :q, '%') or p.description like concat('%', :q, '%') or :q is null) and
            (p.price >= :min_price or :min_price is null) and
            (p.price <= :max_price or :max_price is null)
            """)
    Collection<? extends Product> search(@Param("q") String q, @Param("min_price") BigDecimal minPrice, @Param("max_price") BigDecimal maxPrice);
    
}
