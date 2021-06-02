package in.francl.urbandollop.interfaces.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.francl.urbandollop.domain.application.ProductService;
import in.francl.urbandollop.domain.datatransfer.product.ProductRequest;
import in.francl.urbandollop.domain.datatransfer.product.ProductResponse;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ContextConfiguration(classes = {ProductController.class})
public class ProductControllerIntegrationTests extends BaseControllerIntegrationTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService productService;
    
    @Autowired
    ObjectMapper objMap;
    
    
    @Test
    public void createNewProductSuccess() throws Exception {
        var product = new ProductResponse(UUID.randomUUID().toString(), "teste", "teste-description", BigDecimal.TEN);
        when(productService.create(any())).thenReturn(product);
        
        var request = new ProductRequest("teste", "teste-description", BigDecimal.TEN);
        
        mockMvc.perform(
                post("/products")
                        .content(objMap.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objMap.writeValueAsString(product)))
        ;
    }
    
    @Test
    @SneakyThrows
    public void createNewProductFail() {
        var product = new ProductResponse(UUID.randomUUID().toString(), "teste", "teste-description", BigDecimal.TEN);
        when(productService.create(any())).thenReturn(product);
        
        var request = new ProductRequest("", "teste-description", BigDecimal.TEN);
        
        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMap.writeValueAsString(request))
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status_code", is(HttpStatus.BAD_REQUEST.value())))
        ;
    }
    
    @Test
    @SneakyThrows
    public void shouldUpdateProduct() {
        var product = new ProductResponse("id", "name", "description", BigDecimal.TEN);
        when(productService.update(anyString(), any())).thenReturn(product);
        
        var productRequest = new ProductRequest("name", "edited-description", BigDecimal.TEN);
        
        mockMvc.perform(
                put("/products/%s".formatted(product.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMap.writeValueAsString(productRequest))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", not(productRequest.description())))
        ;
    }
    
    @Test
    @SneakyThrows
    public void shouldNotUpdateProductInvalidFormat() {
        var product = new ProductResponse("id", "name", "description", BigDecimal.TEN);
        when(productService.update(anyString(), any())).thenReturn(product);
        
        var productRequest = new ProductRequest("", "edited-description", BigDecimal.TEN);
        
        mockMvc.perform(
                put("/products/%s".formatted(product.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMap.writeValueAsString(productRequest))
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status_code", is(HttpStatus.BAD_REQUEST.value())))
        ;
    }
    
    @Test
    @SneakyThrows
    public void shouldNotUpdateProductNotFounded() {
        var product = new ProductResponse("id", "name", "description", BigDecimal.TEN);
        when(productService.update(anyString(), any()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        var productRequest = new ProductRequest("name", "edited-description", BigDecimal.TEN);
        
        mockMvc.perform(
                put("/products/%s".formatted(product.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objMap.writeValueAsString(productRequest))
        ).andDo(print())
                .andExpect(status().isNotFound())
        ;
    }
    
    @Test
    @SneakyThrows
    public void shouldRetrieveListProducts() {
        var products = List.of(
                new ProductResponse("id", "name-1", "description-1", BigDecimal.valueOf(1)),
                new ProductResponse("id", "name-2", "description-2", BigDecimal.valueOf(2)),
                new ProductResponse("id", "name-3", "description-3", BigDecimal.valueOf(3)),
                new ProductResponse("id", "name-4", "description-4", BigDecimal.valueOf(4))
        );
        when(productService.retrieveAll()).thenReturn(products);
        
        mockMvc.perform(
                get("/products")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objMap.writeValueAsString(products)))
        ;
    }
    
    @Test
    @SneakyThrows
    public void shouldFindProductsByNameAndDescription() {
        var products = List.of(
                new ProductResponse("id", "name-1", "description-1", BigDecimal.valueOf(1)),
                new ProductResponse("id", "name-2", "description-2", BigDecimal.valueOf(2)),
                new ProductResponse("id", "name-3", "description-3", BigDecimal.valueOf(3)),
                new ProductResponse("id", "name-4", "description-4", BigDecimal.valueOf(4))
        );
        var q = "1,2,3";
        var qArray = q.split(",");
        var productsFiltered = products.stream()
                .filter(it ->
                        Arrays.stream(qArray).anyMatch( s -> it.description().contains(s) )
                ).collect(Collectors.toList());
        when(productService.search(anyString(), any(), any()))
                .thenReturn(productsFiltered);
        
        mockMvc.perform(
                get("/products/search")
                        .queryParam("q", q)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objMap.writeValueAsString(productsFiltered)))
        ;
    }
    
    @Test
    @SneakyThrows
    public void shouldFindProductsByPrice() {
        var products = List.of(
                new ProductResponse("id", "name-1", "description-1", BigDecimal.valueOf(1)),
                new ProductResponse("id", "name-2", "description-2", BigDecimal.valueOf(2)),
                new ProductResponse("id", "name-3", "description-3", BigDecimal.valueOf(3)),
                new ProductResponse("id", "name-4", "description-4", BigDecimal.valueOf(4))
        );
        var minPrice = 2L;
        var maxPrice = 4L;
        
        var productsFiltered = products.stream()
                .filter(it ->
                        it.price().doubleValue() >= minPrice && it.price().doubleValue() <= maxPrice
                ).collect(Collectors.toList());
        when(productService.search(any(), any(), any()))
                .thenReturn(productsFiltered);
        
        mockMvc.perform(
                get("/products/search")
                        .queryParam("max_price", String.valueOf(maxPrice))
                        .queryParam("min_price", String.valueOf(minPrice))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objMap.writeValueAsString(productsFiltered)))
        ;
    }
    
}
