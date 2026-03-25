
package com.wipro.orderservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.wipro.orderservice.dto.OrderEvent;
import com.wipro.orderservice.dto.OrderRequestDto;
import com.wipro.orderservice.dto.OrderResponseDto;
import com.wipro.orderservice.entity.Orders;
import com.wipro.orderservice.feign.ProductClient;
import com.wipro.orderservice.feign.UserClient;
import com.wipro.orderservice.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductClient productFeignClient;

    @Autowired
    UserClient userFeignClient;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackCreateOrder")
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        Object userObj = userFeignClient.getUserById(orderRequestDto.getUserId());
        Map<String, Object> userMap = (Map<String, Object>) userObj;

        if (userMap == null || userMap.get("id") == null) {
            throw new RuntimeException("User not found");
        }

        Object productObj = productFeignClient.getProductById(orderRequestDto.getProductId());
        Map<String, Object> productMap = (Map<String, Object>) productObj;

        if (productMap == null || productMap.get("id") == null) {
            throw new RuntimeException("Product not found");
        }

        int currentQty = ((Number) productMap.get("quantity")).intValue();
        double price = ((Number) productMap.get("price")).doubleValue();

        if (orderRequestDto.getQuantity() <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (currentQty < orderRequestDto.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

      
        Orders order = new Orders();
        order.createOrder(
                orderRequestDto.getUserId(),
                orderRequestDto.getProductId(),
                orderRequestDto.getQuantity(),
                price
        );
        order.validateOrder();

        Orders savedOrder = orderRepository.save(order);

        String productName = (String) productMap.get("name");

        OrderEvent event = new OrderEvent(
                "ORDER_CREATED",
                savedOrder.getId(),
                savedOrder.getProductId(),
                productName,
                savedOrder.getQuantity(),
                LocalDateTime.now()
        );

        kafkaTemplate.send("order-topic", event);
        System.out.println("Kafka Sent: ORDER_CREATED");

        return convertToDto(savedOrder);
    }

    public OrderResponseDto fallbackCreateOrder(OrderRequestDto orderRequestDto, Exception ex) {
        System.out.println("Circuit Breaker Triggered (CREATE ORDER)");

        OrderResponseDto dto = new OrderResponseDto();
        dto.setStatus("FAILED");
        dto.setTotalPrice(0);

        return dto;
    }

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackCancelOrder")
    public OrderResponseDto cancelOrder(int orderId) {

        Optional<Orders> optOrder = orderRepository.findById(orderId);

        if (optOrder.isPresent()) {

            Orders order = optOrder.get();

            Object productObj = productFeignClient.getProductById(order.getProductId());
            Map<String, Object> productMap = (Map<String, Object>) productObj;

            order.cancelOrder();
            order.validateOrder();

            Orders cancelledOrder = orderRepository.save(order);

            String productName = productMap != null ? (String) productMap.get("name") : "UNKNOWN";

            OrderEvent event = new OrderEvent(
                    "ORDER_CANCELLED",
                    orderId,
                    order.getProductId(),
                    productName,
                    order.getQuantity(),
                    LocalDateTime.now()
            );

            kafkaTemplate.send("order-topic", event);
            System.out.println("Kafka Sent: ORDER_CANCELLED");

            return convertToDto(cancelledOrder);
        }

        return null;
    }

    public OrderResponseDto fallbackCancelOrder(int orderId, Exception ex) {
        System.out.println("Circuit Breaker Triggered (CANCEL ORDER)");
        return null;
    }

    @Override
    public List<OrderResponseDto> viewAllOrders() {

        List<Orders> list = orderRepository.findAll();
        List<OrderResponseDto> responseList = new ArrayList<>();

        for (Orders order : list) {
            responseList.add(convertToDto(order));
        }

        return responseList;
    }

    @Override
    public List<OrderResponseDto> viewOrdersByUserId(int userId) {

        List<Orders> list = orderRepository.findByUserId(userId);
        List<OrderResponseDto> responseList = new ArrayList<>();

        for (Orders order : list) {
            responseList.add(convertToDto(order));
        }

        return responseList;
    }

    @Override
    public OrderResponseDto viewOrderById(int orderId) {

        Optional<Orders> optOrder = orderRepository.findById(orderId);

        if (optOrder.isPresent()) {
            return convertToDto(optOrder.get());
        }

        return null;
    }

    private OrderResponseDto convertToDto(Orders order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setProductId(order.getProductId());
        dto.setQuantity(order.getQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }
}