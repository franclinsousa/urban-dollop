package in.francl.urbandollop.bootloader;

import in.francl.urbandollop.ApplicationIntegrationTests;
import in.francl.urbandollop.domain.application.ProductService;
import in.francl.urbandollop.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class UrbanDollopApplicationTests extends ApplicationIntegrationTests {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductService productService;
    
    
    @Test
    void contextLoads() {
        assertAll(
                () -> assertNotNull(productRepository),
                () -> assertNotNull(productService)
        );
    }
    
}
