package consumer.service.controller;


import consumer.service.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer/products")
public class ConsumerController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/healthCheck")
    @Operation(summary = "Check Health of Product Service")
    @ApiResponse(responseCode = "200", description = "Service is working fine")
    public String healthCheck() {
        return "Consumer service working fine";
    }

    // Create a new product
    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productClient.createProduct(product);
        return ResponseEntity.status(201).body(createdProduct);
    }

    // Get all products
    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "List of all products")
    public List<Product> getAllProducts() {
        return productClient.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productClient.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    // Update a product
    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product updatedProduct = productClient.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    // Delete a product
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productClient.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}