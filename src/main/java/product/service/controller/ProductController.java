package product.service.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.service.dto.ErrorResponse;
import product.service.dto.ProductDto;
import product.service.entity.Product;
import product.service.exception.ProductNotFoundException;
import product.service.service.ProductService;
import java.util.List;
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/healthCheck")
    @Operation(summary = "Check Health of Product Service")
    @ApiResponse(responseCode = "200", description = "Service is working fine")
    public String healthCheck() {
        return "Product service working fine";
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            ProductDto response = new ProductDto(
                    createdProduct.getId(),
                    HttpStatus.CREATED.value(),
                    "Product saved successfully"
            );

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ProductDto errorResponse = new ProductDto(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to save product: " + e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "List of all products")
    @ApiResponse(responseCode = "404", description = "No products found")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found in the database.");
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
            return new ResponseEntity<>(product, HttpStatus.OK);  // Return the product if found
        } catch (ProductNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Product with ID " + id + " not found in the database");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            if (updatedProduct != null) {
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else {
                throw new ProductNotFoundException("Product with ID " + id + " not found for update");
            }
        } catch (ProductNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        try {
            boolean isDeleted = productService.deleteProduct(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                throw new ProductNotFoundException("Product with ID " + id + " not found for deletion");
            }
        } catch (ProductNotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/batchUpdate")
    @Operation(summary = "Update multiple products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID in the list")
    })
    public ResponseEntity<Object> updateMultipleProducts(@RequestBody List<Product> products) {
        try {
            productService.updateMultipleProducts(products);
            return new ResponseEntity<>("Products updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}