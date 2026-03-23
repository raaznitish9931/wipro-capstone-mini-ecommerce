// package com.wipro.orderservice.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.wipro.orderservice.dto.CartRequestDto;
// import com.wipro.orderservice.dto.CartResponseDto;
// import com.wipro.orderservice.service.CartService;

// @RestController
// @RequestMapping("/cart")
// public class CartController {

//     @Autowired
//     CartService cartService;

//     @PostMapping("/addProd")
//     ResponseEntity<CartResponseDto> addProductToCart(@RequestBody CartRequestDto cartRequestDto) {
//         CartResponseDto dto = cartService.addProductToCart(cartRequestDto);

//         if (dto != null) {
//             return ResponseEntity.status(HttpStatus.CREATED).body(dto);
//         } else {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//         }
//     }
    

//     @DeleteMapping("/deleteProd/{itemId}")
//     ResponseEntity<String> deleteProductFromCart(@PathVariable int itemId) {
//         boolean flag = cartService.deleteProductFromCart(itemId);

//         if (flag) {
//             return ResponseEntity.ok("Product deleted from cart successfully");
//         } else {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//         }
//     }

//     @PutMapping("/update")
//     ResponseEntity<CartResponseDto> updateCart(@RequestBody CartRequestDto cartRequestDto) {
//         CartResponseDto dto = cartService.updateCart(cartRequestDto);

//         if (dto != null) {
//             return ResponseEntity.ok(dto);
//         } else {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//         }
//     }

//     @GetMapping("/{userId}")
//     ResponseEntity<List<CartResponseDto>> viewCart(@PathVariable int userId) {
//         List<CartResponseDto> list = cartService.viewCart(userId);

//         if ((list != null) && (list.size() != 0)) {
//             return ResponseEntity.ok(list);
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

import com.wipro.orderservice.dto.CartRequestDto;
import com.wipro.orderservice.dto.CartResponseDto;
import com.wipro.orderservice.service.CartService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart APIs", description = "Operations related to shopping cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/addProd")
    @Operation(summary = "Add product to cart", description = "Add product with quantity to user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added to cart"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<CartResponseDto> addProductToCart(@RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto dto = cartService.addProductToCart(cartRequestDto);

        if (dto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/deleteProd/{itemId}")
    @Operation(summary = "Delete product from cart", description = "Remove product from cart using item ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid item ID")
    })
    ResponseEntity<String> deleteProductFromCart(@PathVariable int itemId) {
        boolean flag = cartService.deleteProductFromCart(itemId);

        if (flag) {
            return ResponseEntity.ok("Product deleted from cart successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update cart", description = "Update product quantity in cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<CartResponseDto> updateCart(@RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto dto = cartService.updateCart(cartRequestDto);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "View cart", description = "Get all cart items for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Cart is empty")
    })
    ResponseEntity<List<CartResponseDto>> viewCart(@PathVariable int userId) {
        List<CartResponseDto> list = cartService.viewCart(userId);

        if ((list != null) && (list.size() != 0)) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}