package consumer.service.controller;


import consumer.service.entity.Product;
import org.springframework.web.bind.annotation.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${product-service.url}")
public interface ProductClient {

    @PostMapping
    Product createProduct(@RequestBody Product product);

    @GetMapping
    List<Product> getAllProducts();

    @GetMapping("/{id}")
    Product getProductById(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") Long id);
}