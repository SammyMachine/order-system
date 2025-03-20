package org.example.productservice.model.dto;


public record ReduceQuantityDto(
        Long productId,
        Integer quantityToReduce
) {
}
