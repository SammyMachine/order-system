package org.example.productservice.repository;

import org.example.productservice.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // Товары, у которых количество > 0
    List<ProductEntity> findByQuantityGreaterThan(Integer quantity);

    // Товары, у которых количество <= 5 (на пополнение)
    List<ProductEntity> findByQuantityLessThanEqual(Integer quantity);

    // Товары для конкретного поставщика
    List<ProductEntity> findBySupplierId(Long supplierId);
}
