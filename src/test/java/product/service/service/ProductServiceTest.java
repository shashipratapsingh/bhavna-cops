package product.service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.service.entity.Product;
import product.service.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_ShouldReturnSavedProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertEquals(1L, savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.getProductById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals(1L, foundProduct.get().getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_ShouldReturnEmptyOptional_WhenProductDoesNotExist() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.getProductById(1L);

        assertFalse(foundProduct.isPresent());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void updateProduct_ShouldUpdateAndReturnProduct_WhenProductExists() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Updated Product");

        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProduct(1L, product);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct_ShouldReturnNull_WhenProductDoesNotExist() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.existsById(1L)).thenReturn(false);

        Product updatedProduct = productService.updateProduct(1L, product);

        assertNull(updatedProduct);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).save(product);
    }

    @Test
    void deleteProduct_ShouldReturnTrue_WhenProductExists() {
        when(productRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = productService.deleteProduct(1L);

        assertTrue(isDeleted);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduct_ShouldReturnFalse_WhenProductDoesNotExist() {
        when(productRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = productService.deleteProduct(1L);

        assertFalse(isDeleted);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).deleteById(1L);
    }

    @Test
    void updateMultipleProducts_ShouldUpdateAllProducts_WhenAllExist() {
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);

        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.existsById(2L)).thenReturn(true);

        productService.updateMultipleProducts(Arrays.asList(product1, product2));

        verify(productRepository, times(1)).save(product1);
        verify(productRepository, times(1)).save(product2);
    }
}