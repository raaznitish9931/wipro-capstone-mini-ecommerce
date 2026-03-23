// package com.wipro.orderservice.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.wipro.orderservice.dto.OrderRequestDto;
// import com.wipro.orderservice.dto.OrderResponseDto;
// import com.wipro.orderservice.service.OrderService;

// @RestController
// @RequestMapping("/order")
// public class OrderController {

//     @Autowired
//     OrderService orderService;

//     @PostMapping
//     ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
//         OrderResponseDto dto = orderService.createOrder(orderRequestDto);

//         if (dto != null) {
//             return ResponseEntity.status(HttpStatus.CREATED).body(dto);
//         } else {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//         }
//     }

//     @PutMapping("/{orderId}")
//     ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable int orderId) {
//         OrderResponseDto dto = orderService.cancelOrder(orderId);

//         if (dto != null) {
//             return ResponseEntity.ok(dto);
//         } else {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//         }
//     }

//     @GetMapping
//     ResponseEntity<List<OrderResponseDto>> viewAllOrders() {
//         List<OrderResponseDto> list = orderService.viewAllOrders();

//         if ((list != null) && (list.size() != 0)) {
//             return ResponseEntity.ok(list);
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//         }
//     }

//     @GetMapping("/user/{userId}")
//     ResponseEntity<List<OrderResponseDto>> viewOrdersByUserId(@PathVariable int userId) {
//         List<OrderResponseDto> list = orderService.viewOrdersByUserId(userId);

//         if ((list != null) && (list.size() != 0)) {
//             return ResponseEntity.ok(list);
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//         }
//     }

//     @GetMapping("/{orderId}/detail")
//     ResponseEntity<OrderResponseDto> viewOrderById(@PathVariable int orderId) {
//         OrderResponseDto dto = orderService.viewOrderById(orderId);

//         if (dto != null) {
//             return ResponseEntity.ok(dto);
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//         }
//     }
// }





package com.wipro.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.orderservice.dto.OrderRequestDto;
import com.wipro.orderservice.dto.OrderResponseDto;
import com.wipro.orderservice.service.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/order")
@Tag(name = "Order APIs", description = "Operations related to order management")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    @Operation(summary = "Create order", description = "Place a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto dto = orderService.createOrder(orderRequestDto);

        if (dto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Cancel order", description = "Cancel an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order ID")
    })
    ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable int orderId) {
        OrderResponseDto dto = orderService.cancelOrder(orderId);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "View all orders", description = "Get list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No orders found")
    })
    ResponseEntity<List<OrderResponseDto>> viewAllOrders() {
        List<OrderResponseDto> list = orderService.viewAllOrders();

        if ((list != null) && (list.size() != 0)) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "View orders by user", description = "Get orders of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User orders fetched"),
            @ApiResponse(responseCode = "404", description = "No orders found")
    })
    ResponseEntity<List<OrderResponseDto>> viewOrdersByUserId(@PathVariable int userId) {
        List<OrderResponseDto> list = orderService.viewOrdersByUserId(userId);

        if ((list != null) && (list.size() != 0)) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{orderId}/detail")
    @Operation(summary = "View order details", description = "Get details of a specific order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order details fetched"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    ResponseEntity<OrderResponseDto> viewOrderById(@PathVariable int orderId) {
        OrderResponseDto dto = orderService.viewOrderById(orderId);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}