package com.wipro.ApiGateWay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {

    @GetMapping("/fallback/product")
    public ResponseEntity<Map<String, Object>> productFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "Product Service is currently unavailable. Please try again later.");
        response.put("service", "product-service");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/fallback/order")
    public ResponseEntity<Map<String, Object>> orderFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "Order Service is currently unavailable. Please try again later.");
        response.put("service", "order-service");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/fallback/user")
    public ResponseEntity<Map<String, Object>> userFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "User Profile Service is currently unavailable. Please try again later.");
        response.put("service", "user-service");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
