package com.microservices.productservice.controller;

import com.microservices.productservice.dto.BaseResponse;
import com.microservices.productservice.dto.request.ProductRequestDto;
import com.microservices.productservice.dto.response.ProductResponseDto;
import com.microservices.productservice.service.ProductService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody ProductRequestDto productRequestDto){
        productService.create(productRequestDto);
        return new ResponseEntity<>(BaseResponse.success("SUCCESS"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductResponseDto>>> getAll(){
        return new ResponseEntity<>(BaseResponse.success(productService.getAll(),"SUCCESS"),HttpStatus.OK);
    }

}
