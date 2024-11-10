package com.parallelJMH.ParallelPerformance.SpringWeb;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private List<Product> productList = new ArrayList<>();

    @GetMapping
    public List<Product> getAllProducts() {
        return productList;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        productList.add(product);
        return product;
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
        }
        return product;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }
}
