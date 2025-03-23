package org.example.ordersservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.model.OrderItem;
import org.example.ordersservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderItem orderItem) {
        try {
            orderService.createOrder(orderItem);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully!");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Product not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            } else if (e.getMessage().contains("Not enough quantity for the product")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough quantity for the product");
            }
        }
        return null;
    }


    @GetMapping
    public List<OrderItem> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
