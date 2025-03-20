package org.example.frontendservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;

    // Продукты
    public String getAllProducts() {
        String url = "http://localhost:8081/api/v1/products"; // Путь к API микросервиса товаров
        return restTemplate.getForObject(url, String.class);
    }

    public String createProduct(String productData) {
        String url = "http://localhost:8081/api/v1/products"; // Путь к API микросервиса товаров
        return restTemplate.postForObject(url, productData, String.class);
    }

    public String updateProduct(Long id, String productData) {
        String url = "http://localhost:8081/api/v1/products/" + id; // Путь к API для обновления товара
        restTemplate.put(url, productData);
        return "Product updated successfully";
    }

    public String deleteProduct(Long id) {
        String url = "http://localhost:8081/api/v1/products/" + id; // Путь к API для удаления товара
        restTemplate.delete(url);
        return "Product deleted successfully";
    }

    public String getAvailableProducts() {
        String url = "http://localhost:8081/api/v1/products/available"; // Путь к API для получения доступных товаров
        return restTemplate.getForObject(url, String.class);
    }

    public String getProductsToReplenish() {
        String url = "http://localhost:8081/api/v1/products/replenish"; // Путь к API для получения товаров для пополнения
        return restTemplate.getForObject(url, String.class);
    }

    public String getProductsBySupplier(Long supplierId) {
        String url = "http://localhost:8081/api/v1/products/supplier/" + supplierId; // Путь к API для получения товаров поставщика
        return restTemplate.getForObject(url, String.class);
    }

    // Поставщики
    public String getAllSuppliers() {
        String url = "http://localhost:8082/api/v1/suppliers"; // Путь к API микросервиса поставщиков
        return restTemplate.getForObject(url, String.class);
    }

    public String createSupplier(String supplierData) {
        String url = "http://localhost:8082/api/v1/suppliers"; // Путь к API для создания поставщика
        return restTemplate.postForObject(url, supplierData, String.class);
    }

    public String updateSupplier(Long id, String supplierData) {
        String url = "http://localhost:8082/api/v1/suppliers/" + id; // Путь к API для обновления поставщика
        restTemplate.put(url, supplierData);
        return "Supplier updated successfully";
    }

    public String deleteSupplier(Long id) {
        String url = "http://localhost:8082/api/v1/suppliers/" + id; // Путь к API для удаления поставщика
        restTemplate.delete(url);
        return "Supplier deleted successfully";
    }

    // Заказы
    public String createOrder(String orderData) {
        String url = "http://localhost:8083/api/v1/orders"; // Путь к API микросервиса заказов
        return restTemplate.postForObject(url, orderData, String.class);
    }

    public String getOrder(Long orderId) {
        String url = "http://localhost:8083/api/v1/orders/" + orderId; // Путь к API для получения заказа по ID
        return restTemplate.getForObject(url, String.class);
    }

    public String getAllOrders() {
        String url = "http://localhost:8083/api/v1/orders"; // Путь к API для получения всех заказов
        return restTemplate.getForObject(url, String.class);
    }
}
