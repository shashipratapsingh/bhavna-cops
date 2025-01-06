package product.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import product.service.entity.Product;
import product.service.exception.ProductNotFoundException;
import product.service.service.ProductService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/products/healthCheck"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product service working fine"));
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setName("Test Product");

        Product createdProduct = new Product();
        createdProduct.setId(1L);
        createdProduct.setName("Test Product");

        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Product saved successfully"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");

        List<Product> products = Arrays.asList(product1, product2);

        Mockito.when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    void testGetAllProducts_NotFound() throws Exception {
        // Mock the service to throw the exception
        Mockito.when(productService.getAllProducts())
                .thenThrow(new ProductNotFoundException("No products found in the database."));

        // Perform the GET request and expect the exception message
        mockMvc.perform(get("/products"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No products found in the database."));
    }


    @Test
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");

        Mockito.when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        Mockito.when(productService.getProductById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with ID 1 not found in the database"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setName("Updated Product");

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product");

        Mockito.when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        Product product = new Product();
        product.setName("Updated Product");

        Mockito.when(productService.updateProduct(anyLong(), any(Product.class))).thenThrow(new ProductNotFoundException("Product with ID 1 not found for update"));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with ID 1 not found for update"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.when(productService.deleteProduct(1L)).thenReturn(true);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProduct_NotFound() throws Exception {
        Mockito.when(productService.deleteProduct(1L)).thenThrow(new ProductNotFoundException("Product with ID 1 not found for deletion"));

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with ID 1 not found for deletion"));
    }
}
