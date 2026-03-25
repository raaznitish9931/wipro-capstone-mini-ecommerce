
package com.wipro.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.productservice.dto.ProductRequestDto;
import com.wipro.productservice.dto.ProductResponseDto;
import com.wipro.productservice.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/product")
@Tag(name = "Product APIs", description = "Product management operations")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Fetch list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    ResponseEntity<List<ProductResponseDto>> findAll() {
        List<ProductResponseDto> list = productService.findAll();

        if ((list != null) && (list.size() != 0)) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Fetch product details using product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    ResponseEntity<ProductResponseDto> findById(@PathVariable int id) {
        ProductResponseDto product = productService.findById(id);

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID")
    })
    ResponseEntity<String> deleteById(@PathVariable int id) {
        boolean flag = productService.deleteById(id);

        if (flag) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Add a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    ResponseEntity<ProductResponseDto> save(@RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productSaved = productService.save(productRequestDto);

        if (productSaved != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(productSaved);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update existing product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update request")
    })
    ResponseEntity<ProductResponseDto> update(@PathVariable int id, @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productUpdated = productService.update(id, productRequestDto);

        if (productUpdated != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productUpdated);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
}