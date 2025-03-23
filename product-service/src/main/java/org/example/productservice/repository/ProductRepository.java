package org.example.productservice.repository;

import org.example.productservice.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByQuantityGreaterThan(Integer quantity);

    List<ProductEntity> findByQuantityLessThanEqual(Integer quantity);

    List<ProductEntity> findBySupplierId(Long supplierId);
}
