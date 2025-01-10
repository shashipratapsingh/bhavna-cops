/*
package consumer.service.controller;

import consumer.service.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsumerControllerTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private ConsumerController consumerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHealthCheck() {
        String response = consumerController.healthCheck();
        assertEquals("Consumer service working fine", response);
    }

    @Test
    void testGetAllProducts_Success() {
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Product1", 100.0, 10, "Description1", null, null),
                new Product(2L, "Product2", 200.0, 5, "Description2", null, null)
        );

        when(productClient.getAllProducts()).thenReturn(mockProducts);

        ResponseEntity<?> response = consumerController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducts, response.getBody());
        verify(productClient, times(1)).getAllProducts();
    }

    @Test
    void testGetAllProducts_InternalServerError() {
        when(productClient.getAllProducts()).thenThrow(new RuntimeException("Error fetching products"));

        ResponseEntity<?> response = consumerController.getAllProducts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to fetch products. Please try again later.", response.getBody());
        verify(productClient, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Success() {
        Product mockProduct = new Product(1L, "Product1", 100.0, 10, "Description1", null, null);

        when(productClient.getProductById(1L)).thenReturn(mockProduct);

        ResponseEntity<?> response = consumerController.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
        verify(productClient, times(1)).getProductById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productClient.getProductById(1L)).thenReturn(null);

        ResponseEntity<?> response = consumerController.getProductById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found with ID: 1", response.getBody());
        verify(productClient, times(1)).getProductById(1L);
    }

    @Test
    void testUpdateProduct_Success() {
        Product mockProduct = new Product(1L, "UpdatedProduct", 150.0, 15, "UpdatedDescription", null, null);

        when(productClient.updateProduct(eq(1L), any(Product.class))).thenReturn(mockProduct);

        Product updatedProduct = new Product(1L, "UpdatedProduct", 150.0, 15, "UpdatedDescription", null, null);

        ResponseEntity<Product> response = consumerController.updateProduct(1L, updatedProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
        verify(productClient, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        doNothing().when(productClient).deleteProduct(1L);

        ResponseEntity<Void> response = consumerController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productClient, times(1)).deleteProduct(1L);
    }
}
*/
