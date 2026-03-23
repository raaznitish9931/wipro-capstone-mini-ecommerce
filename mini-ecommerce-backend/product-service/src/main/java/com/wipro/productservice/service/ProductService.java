package com.wipro.productservice.service;

import java.util.List;

import com.wipro.productservice.dto.ProductRequestDto;
import com.wipro.productservice.dto.ProductResponseDto;

public interface ProductService {

    List<ProductResponseDto> findAll();

    ProductResponseDto findById(int id);

    ProductResponseDto save(ProductRequestDto productRequestDto);

    ProductResponseDto update(int id, ProductRequestDto productRequestDto);

    boolean deleteById(int id);
}