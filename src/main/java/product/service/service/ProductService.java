package product.service.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.service.entity.Product;
import product.service.exception.ProductNotFoundException;
import product.service.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        List<Product> productList= productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return productList;
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not Found")));
    }

    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
        product.setId(existingProduct.getId());
        return productRepository.save(product);
    }


    @Transactional
    public void updateMultipleProducts(List<Product> products) {
        for (Product product : products) {
            if (!productRepository.existsById(product.getId())) {
                throw new IllegalArgumentException("Product with ID " + product.getId() + " does not exist");
            }
            productRepository.save(product);
        }
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }else {
            throw new ProductNotFoundException("Product with ID " + id + " does not exist");
        }
    }
}