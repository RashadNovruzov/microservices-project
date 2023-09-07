package com.microservices.productservice.mapper;

import com.microservices.productservice.dto.request.ProductRequestDto;
import com.microservices.productservice.dto.response.ProductResponseDto;
import com.microservices.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product toEntity(ProductRequestDto productRequestDto);

    ProductResponseDto toResponseDto(Product product);

}
