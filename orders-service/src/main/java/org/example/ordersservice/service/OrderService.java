package org.example.ordersservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ordersservice.model.OrderItem;
import org.example.ordersservice.repository.OrderItemRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public void createOrder(OrderItem orderItem) throws RuntimeException {
        for (int i = 0; i < orderItem.getProductIds().size(); i++) {
            Long productId = orderItem.getProductIds().get(i);
            Integer quantity = orderItem.getQuantities().get(i);

            ReduceQuantityDto request = new ReduceQuantityDto(productId, quantity);
            HttpEntity<ReduceQuantityDto> requestEntity = new HttpEntity<>(request);

            ResponseEntity<Void> response;
            try {
                response = restTemplate.postForEntity("http://localhost:8081/api/v1/products/reduceQuantity", requestEntity, Void.class);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    throw new RuntimeException("Product not found");
                } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    throw new RuntimeException("Not enough quantity for the product");
                } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                    throw new RuntimeException("Internal server error occurred");
                } else {
                    throw new RuntimeException("Unexpected error: " + e.getMessage());
                }
            }

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Product quantity successfully reduced");
            } else {
                throw new RuntimeException("Unexpected error: " + response.getStatusCode());
            }


        }
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrders() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
    }
}

record ReduceQuantityDto(Long productId, Integer quantityToReduce) {
}
