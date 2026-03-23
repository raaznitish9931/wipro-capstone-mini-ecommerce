package com.wipro.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.productservice.dto.ProductRequestDto;
import com.wipro.productservice.dto.ProductResponseDto;
import com.wipro.productservice.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
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