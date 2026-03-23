package com.wipro.orderservice.service;

import java.util.List;

import com.wipro.orderservice.dto.OrderRequestDto;
import com.wipro.orderservice.dto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto cancelOrder(int orderId);

    List<OrderResponseDto> viewAllOrders();

    List<OrderResponseDto> viewOrdersByUserId(int userId);

    OrderResponseDto viewOrderById(int orderId);
}