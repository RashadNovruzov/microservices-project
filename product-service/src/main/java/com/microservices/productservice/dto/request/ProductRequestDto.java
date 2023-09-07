package com.microservices.productservice.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ProductRequestDto {

    private String name;
    private String description;
    private Double price;

}
