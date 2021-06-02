package in.francl.urbandollop.application;

import in.francl.urbandollop.domain.application.ProductService;
import in.francl.urbandollop.domain.datatransfer.product.ProductRequest;
import in.francl.urbandollop.domain.datatransfer.product.ProductResponse;
import in.francl.urbandollop.domain.model.ProductModel;
import in.francl.urbandollop.domain.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository repository;
    
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public ProductResponse create(ProductRequest product) {
        var model = ProductModel.of(
                product.name(),
                product.description(),
                product.price()
        );
        var modelSaved = repository.saveOrUpdate(model);
        return ProductResponse.of(modelSaved);
    }
    
    @Override
    public ProductResponse update(String id, ProductRequest request) {
        var product = repository.findProductById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        var productEdited = product.edit(request.toProduct());
    
        var productSaved = repository.saveOrUpdate(productEdited);
        
        return ProductResponse.of(productSaved);
    }
    
    @Override
    public ProductResponse findOneById(String id) {
        var product = repository.findProductById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ProductResponse.of(product);
    }
    
    @Override
    public Collection<ProductResponse> retrieveAll() {
        return repository.retrieveAll().stream().map(ProductResponse::of).collect(Collectors.toList());
    }
    
    @Override
    public Collection<ProductResponse> search(String q, BigDecimal minPrice, BigDecimal maxPrice) {
        return repository.search(q, minPrice, maxPrice).stream()
                .map(ProductResponse::of).collect(Collectors.toList());
    }
    
    @Override
    public void delete(String id) {
        repository.findProductById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.deleteProduct(id);
    }
    
}
