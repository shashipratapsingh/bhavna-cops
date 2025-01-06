package product.service.service;

import product.service.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product createProduct(Product product);

    public List<Product> getAllProducts();
    public Optional<Product> getProductById(Long id);
    public Product updateProduct(Long id, Product product);
    public void updateMultipleProducts(List<Product> products);

    public boolean deleteProduct(Long id);
}