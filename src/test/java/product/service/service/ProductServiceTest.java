//package product.service.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import product.service.entity.Product;
//import product.service.exception.ProductNotFoundException;
//import product.service.repository.ProductRepository;
//import product.service.service.impl.ProductServiceImpl;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ProductServiceTest {
//
//    @InjectMocks
//    private ProductServiceImpl productService;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateProduct() {
//        Product product = new Product();
//        product.setName("New Product");
//
//        when(productRepository.save(product)).thenReturn(product);
//
//        Product createdProduct = productService.createProduct(product);
//
//        assertNotNull(createdProduct);
//        assertEquals("New Product", createdProduct.getName());
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    void testGetAllProducts_Success() {
//        Product product1 = new Product();
//        product1.setId(1L);
//        product1.setName("Product 1");
//
//        Product product2 = new Product();
//        product2.setId(2L);
//        product2.setName("Product 2");
//
//        List<Product> productList = Arrays.asList(product1, product2);
//
//        when(productRepository.findAll()).thenReturn(productList);
//
//        List<Product> result = productService.getAllProducts();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetAllProducts_NotFound() {
//        when(productRepository.findAll()).thenReturn(List.of());
//
//        ProductNotFoundException exception = assertThrows(
//                ProductNotFoundException.class,
//                () -> productService.getAllProducts()
//        );
//
//        assertEquals("No products found", exception.getMessage());
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetProductById_Success() {
//        Product product = new Product();
//        product.setId(1L);
//        product.setName("Product 1");
//
//        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//
//        Optional<Product> result = productService.getProductById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals("Product 1", result.get().getName());
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testGetProductById_NotFound() {
//        when(productRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ProductNotFoundException exception = assertThrows(
//                ProductNotFoundException.class,
//                () -> productService.getProductById(1L)
//        );
//
//        assertEquals("Product with ID 1 not Found", exception.getMessage());
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testUpdateProduct_Success() {
//        Product existingProduct = new Product();
//        existingProduct.setId(1L);
//        existingProduct.setName("Old Product");
//
//        Product updatedProduct = new Product();
//        updatedProduct.setId(1L);
//        updatedProduct.setName("Updated Product");
//
//        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
//        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
//
//        Product result = productService.updateProduct(1L, updatedProduct);
//
//        assertNotNull(result);
//        assertEquals("Updated Product", result.getName());
//        verify(productRepository, times(1)).findById(1L);
//        verify(productRepository, times(1)).save(updatedProduct);
//    }
//
//    @Test
//    void testUpdateProduct_NotFound() {
//        Product product = new Product();
//        product.setName("Updated Product");
//
//        when(productRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ProductNotFoundException exception = assertThrows(
//                ProductNotFoundException.class,
//                () -> productService.updateProduct(1L, product)
//        );
//
//        assertEquals("Product with ID 1 not found", exception.getMessage());
//        verify(productRepository, times(1)).findById(1L);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    void testUpdateMultipleProducts_Success() {
//        Product product1 = new Product();
//        product1.setId(1L);
//        product1.setName("Product 1");
//
//        Product product2 = new Product();
//        product2.setId(2L);
//        product2.setName("Product 2");
//
//        List<Product> products = Arrays.asList(product1, product2);
//
//        when(productRepository.existsById(1L)).thenReturn(true);
//        when(productRepository.existsById(2L)).thenReturn(true);
//
//        productService.updateMultipleProducts(products);
//
//        verify(productRepository, times(1)).existsById(1L);
//        verify(productRepository, times(1)).existsById(2L);
//        verify(productRepository, times(2)).save(any(Product.class));
//    }
//
//    @Test
//    void testUpdateMultipleProducts_InvalidId() {
//        Product product1 = new Product();
//        product1.setId(1L);
//
//        Product product2 = new Product();
//        product2.setId(2L);
//
//        List<Product> products = Arrays.asList(product1, product2);
//
//        when(productRepository.existsById(1L)).thenReturn(true);
//        when(productRepository.existsById(2L)).thenReturn(false);
//
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> productService.updateMultipleProducts(products)
//        );
//
//        assertEquals("Product with ID 2 does not exist", exception.getMessage());
//        verify(productRepository, times(1)).existsById(1L);
//        verify(productRepository, times(1)).existsById(2L);
//        verify(productRepository, times(1)).save(any(Product.class));
//    }
//
//    @Test
//    void testDeleteProduct_Success() {
//        when(productRepository.existsById(1L)).thenReturn(true);
//
//        boolean result = productService.deleteProduct(1L);
//
//        assertTrue(result);
//        verify(productRepository, times(1)).existsById(1L);
//        verify(productRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testDeleteProduct_NotFound() {
//        when(productRepository.existsById(1L)).thenReturn(false);
//
//        ProductNotFoundException exception = assertThrows(
//                ProductNotFoundException.class,
//                () -> productService.deleteProduct(1L)
//        );
//
//        assertEquals("Product with ID 1 does not exist", exception.getMessage());
//        verify(productRepository, times(1)).existsById(1L);
//        verify(productRepository, never()).deleteById(anyLong());
//    }
//}
