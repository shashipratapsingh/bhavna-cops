package consumer.service.controller;


import consumer.service.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "List of all products")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productClient.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch products. Please try again later.");
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productClient.getProductById(id);
            return product != null
                    ? ResponseEntity.ok(product)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the product. Please try again later.");
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product updatedProduct = productClient.updateProduct(id, product); // This throws ProductNotFoundException if not found
        return ResponseEntity.ok(updatedProduct);
    }



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