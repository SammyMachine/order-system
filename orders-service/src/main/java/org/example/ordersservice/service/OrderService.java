package org.example.ordersservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ordersservice.model.Order;
import org.example.ordersservice.model.OrderItem;
import org.example.ordersservice.repository.OrderItemRepository;
import org.example.ordersservice.repository.OrderRepository;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public Order createOrder(Order order) {
        // Сохраняем заказ
        Order savedOrder = orderRepository.save(order);

        // Для каждой позиции заказа уменьшаем количество товара в product-service
        for (OrderItem item : order.getItems()) {
            // URL для вызова product-service (адрес может отличаться в зависимости от конфигурации)
            String productServiceUrl = "http://localhost:8081/api/v1/products/reduceQuantity";
            ReduceQuantityRequest requestBody = new ReduceQuantityRequest();
            requestBody.setProductId(item.getProductId());
            requestBody.setQuantityToReduce(item.getQuantity());
            HttpEntity<ReduceQuantityRequest> request = new HttpEntity<>(requestBody);
            restTemplate.postForObject(productServiceUrl, request, Void.class);

            // Связываем позицию заказа с заказом и сохраняем
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return savedOrder;
    }
}

// DTO для запроса к product-service
class ReduceQuantityRequest {
    private Long productId;
    private Integer quantityToReduce;

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Integer getQuantityToReduce() {
        return quantityToReduce;
    }
    public void setQuantityToReduce(Integer quantityToReduce) {
        this.quantityToReduce = quantityToReduce;
    }
}
