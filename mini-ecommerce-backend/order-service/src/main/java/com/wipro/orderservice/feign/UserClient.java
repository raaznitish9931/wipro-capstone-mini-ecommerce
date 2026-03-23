package com.wipro.orderservice.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERMS-R")
public interface UserClient {

  @GetMapping("/user-r/{id}")  
    public Map<String, Object> getUserById(@PathVariable int id);
}