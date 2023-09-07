package com.microservices.productservice.service;

import com.microservices.productservice.dto.request.ProductRequestDto;
import com.microservices.productservice.dto.response.ProductResponseDto;
import com.microservices.productservice.mapper.ProductMapper;
import com.microservices.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void create(ProductRequestDto productRequestDto) {
        productRepository.save(productMapper.toEntity(productRequestDto));
        log.debug("product {} is saved",productRequestDto);
    }

    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream().map(productMapper::toResponseDto).toList();
    }
}
