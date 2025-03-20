package org.example.productservice.controller;
import org.example.productservice.model.dto.ReduceQuantityDto;
import org.example.productservice.model.entity.ProductEntity;
import org.example.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity productEntity) {
        ProductEntity created = productService.createProduct(productEntity);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity productEntity) {
        ProductEntity updated = productService.updateProduct(id, productEntity);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/available")
    public List<ProductEntity> getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    @GetMapping("/replenish")
    public List<ProductEntity> getProductsToReplenish() {
        return productService.getProductsToReplenish();
    }

    @GetMapping("/supplier/{supplierId}")
    public List<ProductEntity> getProductsBySupplier(@PathVariable Long supplierId) {
        return productService.getProductsBySupplier(supplierId);
    }

    // Endpoint для уменьшения количества товара
    @PostMapping("/reduceQuantity")
    public ResponseEntity<Void> reduceQuantity(@RequestBody ReduceQuantityDto request) {
        productService.reduceQuantity(request.productId(), request.quantityToReduce());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

