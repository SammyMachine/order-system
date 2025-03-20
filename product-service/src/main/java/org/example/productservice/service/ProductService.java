package org.example.productservice.service;

import jakarta.transaction.Transactional;
import org.example.productservice.model.entity.ProductEntity;
import org.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity createProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public ProductEntity updateProduct(Long id, ProductEntity productEntityDetails) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productEntity.setName(productEntityDetails.getName());
        productEntity.setStorageLocation(productEntityDetails.getStorageLocation());
        productEntity.setQuantity(productEntityDetails.getQuantity());
        productEntity.setSupplierId(productEntityDetails.getSupplierId());
        return productRepository.save(productEntity);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductEntity> getAvailableProducts() {
        return productRepository.findByQuantityGreaterThan(0);
    }

    public List<ProductEntity> getProductsToReplenish() {
        return productRepository.findByQuantityLessThanEqual(5);
    }

    public List<ProductEntity> getProductsBySupplier(Long supplierId) {
        return productRepository.findBySupplierId(supplierId);
    }

    // Метод для уменьшения количества товара при создании заказа
    public void reduceQuantity(Long productId, Integer quantityToReduce) throws IllegalArgumentException {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (productEntity.getQuantity() < quantityToReduce) {
            throw new IllegalArgumentException("Not enough quantity for product: " + productId);
        }
        productEntity.setQuantity(productEntity.getQuantity() - quantityToReduce);
        productRepository.save(productEntity);
    }
}
