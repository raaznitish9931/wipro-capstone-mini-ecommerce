package com.wipro.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.orderservice.dto.CartRequestDto;
import com.wipro.orderservice.dto.CartResponseDto;
import com.wipro.orderservice.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addProd")
    ResponseEntity<CartResponseDto> addProductToCart(@RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto dto = cartService.addProductToCart(cartRequestDto);

        if (dto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    

    @DeleteMapping("/deleteProd/{itemId}")
    ResponseEntity<String> deleteProductFromCart(@PathVariable int itemId) {
        boolean flag = cartService.deleteProductFromCart(itemId);

        if (flag) {
            return ResponseEntity.ok("Product deleted from cart successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update")
    ResponseEntity<CartResponseDto> updateCart(@RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto dto = cartService.updateCart(cartRequestDto);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<CartResponseDto>> viewCart(@PathVariable int userId) {
        List<CartResponseDto> list = cartService.viewCart(userId);

        if ((list != null) && (list.size() != 0)) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}