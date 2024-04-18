package com.nikola.spring.service;

import com.nikola.spring.dto.CategoryDto;
import com.nikola.spring.dto.ProductDto;

import java.util.List;

public interface ProductsService {

    ProductDto addProduct(ProductDto productDto);

    List<ProductDto> listAll();

    ProductDto getProductById(Integer productId);

    ProductDto updateProductById(Integer productId, ProductDto productDto);

    void deleteProduct(Integer productId);

    List<ProductDto> listAllByCategory(Integer categoryId);




}
