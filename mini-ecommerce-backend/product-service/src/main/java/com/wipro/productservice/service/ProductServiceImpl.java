package com.wipro.productservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.productservice.dto.ProductRequestDto;
import com.wipro.productservice.dto.ProductResponseDto;
import com.wipro.productservice.entity.Product;
import com.wipro.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductResponseDto> findAll() {

        List<Product> list = productRepository.findAll();
        List<ProductResponseDto> responseList = new ArrayList<>();

        for (Product product : list) {
            ProductResponseDto dto = new ProductResponseDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setQuantity(product.getQuantity());
            dto.setCategory(product.getCategory());
            dto.setImageUrl(product.getImageUrl());
            dto.setBrand(product.getBrand());
            dto.setRating(product.getRating());

            responseList.add(dto);
        }

        return responseList;
    }

    @Override
    public ProductResponseDto findById(int id) {

        Optional<Product> optProd = productRepository.findById(id);

        if (!optProd.isEmpty()) {
            Product product = optProd.get();

            ProductResponseDto dto = new ProductResponseDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setQuantity(product.getQuantity());
            dto.setCategory(product.getCategory());
            dto.setImageUrl(product.getImageUrl());
            dto.setBrand(product.getBrand());
            dto.setRating(product.getRating());

            return dto;
        }

        return null;
    }

    @Override
    public ProductResponseDto save(ProductRequestDto productRequestDto) {

        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setQuantity(productRequestDto.getQuantity());
        product.setCategory(productRequestDto.getCategory());
        product.setImageUrl(productRequestDto.getImageUrl());
        product.setBrand(productRequestDto.getBrand());
        product.setRating(productRequestDto.getRating());

        Product productSaved = productRepository.save(product);

        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(productSaved.getId());
        dto.setName(productSaved.getName());
        dto.setDescription(productSaved.getDescription());
        dto.setPrice(productSaved.getPrice());
        dto.setQuantity(productSaved.getQuantity());
        dto.setCategory(productSaved.getCategory());
        dto.setImageUrl(productSaved.getImageUrl());
        dto.setBrand(productSaved.getBrand());
        dto.setRating(productSaved.getRating());

        return dto;
    }

    @Override
    public ProductResponseDto update(int id, ProductRequestDto productRequestDto) {

        Optional<Product> optProd = productRepository.findById(id);

        if (!optProd.isEmpty()) {
            Product product = optProd.get();

            product.setName(productRequestDto.getName());
            product.setDescription(productRequestDto.getDescription());
            product.setPrice(productRequestDto.getPrice());
            product.setQuantity(productRequestDto.getQuantity());
            product.setCategory(productRequestDto.getCategory());
            product.setImageUrl(productRequestDto.getImageUrl());
            product.setBrand(productRequestDto.getBrand());
            product.setRating(productRequestDto.getRating());

            Product updatedProduct = productRepository.save(product);

            ProductResponseDto dto = new ProductResponseDto();
            dto.setId(updatedProduct.getId());
            dto.setName(updatedProduct.getName());
            dto.setDescription(updatedProduct.getDescription());
            dto.setPrice(updatedProduct.getPrice());
            dto.setQuantity(updatedProduct.getQuantity());
            dto.setCategory(updatedProduct.getCategory());
            dto.setImageUrl(updatedProduct.getImageUrl());
            dto.setBrand(updatedProduct.getBrand());
            dto.setRating(updatedProduct.getRating());

            return dto;
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        boolean flag = false;

        try {
            productRepository.deleteById(id);
            flag = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return flag;
    }
}