package com.wipro.orderservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.orderservice.dto.CartRequestDto;
import com.wipro.orderservice.dto.CartResponseDto;
import com.wipro.orderservice.entity.Cart;
import com.wipro.orderservice.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public CartResponseDto addProductToCart(CartRequestDto cartRequestDto) {

        Cart cart = new Cart();
        cart.setUserId(cartRequestDto.getUserId());
        cart.setProductId(cartRequestDto.getProductId());
        cart.setQuantity(cartRequestDto.getQuantity());

        Cart cartSaved = cartRepository.save(cart);

        CartResponseDto dto = new CartResponseDto();
        dto.setId(cartSaved.getId());
        dto.setUserId(cartSaved.getUserId());
        dto.setProductId(cartSaved.getProductId());
        dto.setQuantity(cartSaved.getQuantity());

        return dto;
    }

    @Override
    public boolean deleteProductFromCart(int itemId) {
        boolean flag = false;

        try {
            cartRepository.deleteById(itemId);
            flag = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return flag;
    }

    @Override
    public CartResponseDto updateCart(CartRequestDto cartRequestDto) {

        List<Cart> cartList = cartRepository.findByUserId(cartRequestDto.getUserId());

        for (Cart cart : cartList) {
            if (cart.getProductId() == cartRequestDto.getProductId()) {
                cart.setQuantity(cartRequestDto.getQuantity());

                Cart updatedCart = cartRepository.save(cart);

                CartResponseDto dto = new CartResponseDto();
                dto.setId(updatedCart.getId());
                dto.setUserId(updatedCart.getUserId());
                dto.setProductId(updatedCart.getProductId());
                dto.setQuantity(updatedCart.getQuantity());

                return dto;
            }
        }

        return null;
    }

    @Override
    public List<CartResponseDto> viewCart(int userId) {

        List<Cart> list = cartRepository.findByUserId(userId);
        List<CartResponseDto> responseList = new ArrayList<>();

        for (Cart cart : list) {
            CartResponseDto dto = new CartResponseDto();
            dto.setId(cart.getId());
            dto.setUserId(cart.getUserId());
            dto.setProductId(cart.getProductId());
            dto.setQuantity(cart.getQuantity());

            responseList.add(dto);
        }

        return responseList;
    }
}