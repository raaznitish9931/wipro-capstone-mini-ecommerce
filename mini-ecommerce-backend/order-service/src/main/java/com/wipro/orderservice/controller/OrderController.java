package com.wipro.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.orderservice.dto.OrderRequestDto;
import com.wipro.orderservice.dto.OrderResponseDto;
import com.wipro.orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto dto = orderService.createOrder(orderRequestDto);

        if (dto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{orderId}")
    ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable int orderId) {
        OrderResponseDto dto = orderService.cancelOrder(orderId);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    ResponseEntity<List<OrderResponseDto>> viewAllOrders() {
        List<OrderResponseDto> list = orderService.viewAllOrders();

        if ((list != null) && (list.size() != 0)) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<List<OrderResponseDto>> viewOrdersByUserId(@PathVariable int userId) {
        List<OrderResponseDto> list = orderService.viewOrdersByUserId(userId);

        if ((list != null) && (list.size() != 0)) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{orderId}/detail")
    ResponseEntity<OrderResponseDto> viewOrderById(@PathVariable int orderId) {
        OrderResponseDto dto = orderService.viewOrderById(orderId);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}