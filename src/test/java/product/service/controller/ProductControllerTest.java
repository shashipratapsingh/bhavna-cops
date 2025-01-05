package product.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import product.service.entity.Product;
import product.service.service.ProductService;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(99.99);
    }

    @Test
    @DisplayName("Should return health check message")
    void healthCheck() throws Exception {
        mockMvc.perform(get("/products/healthCheck"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product service working fine"));
    }

    @Test
    @DisplayName("Should create a new product")
    void createProduct() throws Exception {
        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    @DisplayName("Should fetch all products")
    void getAllProducts() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(product.getName()));
    }

    @Test
    @DisplayName("Should fetch product by ID")
    void getProductById() throws Exception {
        Mockito.when(productService.getProductById(anyLong())).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    @DisplayName("Should return 404 when product not found by ID")
    void getProductByIdNotFound() throws Exception {
        Mockito.when(productService.getProductById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update a product")
    void updateProduct() throws Exception {
        Mockito.when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    @DisplayName("Should return 404 when updating non-existent product")
    void updateProductNotFound() throws Exception {
        Mockito.when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(null);
        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete a product")
    void deleteProduct() throws Exception {
        Mockito.when(productService.deleteProduct(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existent product")
    void deleteProductNotFound() throws Exception {
        Mockito.when(productService.deleteProduct(anyLong())).thenReturn(false);
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound());
    }
}
