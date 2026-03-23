package com.wipro.orderservice.service;

import java.util.List;

import com.wipro.orderservice.dto.CartRequestDto;
import com.wipro.orderservice.dto.CartResponseDto;

public interface CartService {

    CartResponseDto addProductToCart(CartRequestDto cartRequestDto);

    boolean deleteProductFromCart(int itemId);

    CartResponseDto updateCart(CartRequestDto cartRequestDto);

    List<CartResponseDto> viewCart(int userId);
}