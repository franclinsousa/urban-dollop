package in.francl.urbandollop.interfaces.api;


import in.francl.urbandollop.domain.application.ProductService;
import in.francl.urbandollop.domain.datatransfer.product.ProductRequest;
import in.francl.urbandollop.domain.datatransfer.product.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping("/products")
public final class ProductController extends BaseController {
    
    private final ProductService service;
    
    public ProductController(final ProductService service) {
        this.service = service;
    }
    
    /**
     * Criação de um produto.
     */
    @PostMapping({"", "/"})
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        var product = service.create(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    
    /**
     * Atualização de um produto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable String id,
            @RequestBody @Valid ProductRequest productRequest
    ) {
        var product = service.update(id, productRequest);
        return ResponseEntity.ok(product);
    }
    
    /**
     * Busca de um produto por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable String id) {
        var product = service.findOneById(id);
        return ResponseEntity.ok(product);
    }
    
    /**
     * Lista de produtos.
     */
    @GetMapping({"", "/"})
    public ResponseEntity<Collection<ProductResponse>> findAll() {
        var products = service.retrieveAll();
        return ResponseEntity.ok(products);
    }
    
    /**
     * Lista de produtos filtrados.
     */
    @GetMapping("/search")
    public ResponseEntity<Collection<ProductResponse>> search(
            @RequestParam(value = "q", required = false) String search,
            @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
            @RequestParam(value = "max_price", required = false) BigDecimal maxPrice
    ) {
        var resultSearch = service.search(search, minPrice, maxPrice);
        return ResponseEntity.ok(resultSearch);
    }
    
    /**
     * Deleção de um produto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
