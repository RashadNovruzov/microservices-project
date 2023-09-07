package com.microservices.productservice.dto.response;

import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
