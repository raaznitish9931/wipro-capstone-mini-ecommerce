package com.wipro.orderservice.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/product/{id}")
    public Map<String, Object> getProductById(@PathVariable int id);

    // ✅ FIXED METHOD
    @PutMapping("/product/{id}")
    public Map<String, Object> updateProduct(
            @PathVariable int id,
            @RequestBody Map<String, Object> productMap
    );
}