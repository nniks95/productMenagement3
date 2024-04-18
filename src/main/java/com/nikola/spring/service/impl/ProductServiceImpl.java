package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.ProductDto;
import com.nikola.spring.entities.CategoryEntity;
import com.nikola.spring.entities.ProductEntity;
import com.nikola.spring.exceptions.DataNotValidatedException;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CategoryRepository;
import com.nikola.spring.repositories.ProductRepository;
import com.nikola.spring.service.CategoryService;
import com.nikola.spring.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements ProductsService {

    @Autowired
    private TempConverter tempConverter;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;


    @Override
    public ProductDto addProduct(ProductDto productDto) {
        categoryService.getCategoryById(productDto.getCategoryId());
        ProductEntity productEntity = tempConverter.dtoToEntity(productDto);
        ProductEntity storedProduct = productRepository.save(productEntity);
        return tempConverter.entityToDto(storedProduct);

    }

    @Override
    public List<ProductDto> listAll() {

        List<ProductDto> returnValue = new ArrayList<>();
        List<ProductEntity> allProducts = productRepository.findAll();

        for(ProductEntity productEntity:allProducts){
            ProductDto productDto = tempConverter.entityToDto(productEntity);
            returnValue.add(productDto);
        }

            return returnValue;
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        ProductDto returnValue = null;
        Optional<ProductEntity> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()){
            ProductEntity productEntity = productOptional.get();
            returnValue = tempConverter.entityToDto(productEntity);
        }else{
            throw new InstanceUndefinedException(new Error("Product undefined"));
        }

        return returnValue;
    }

    @Override
    public ProductDto updateProductById(Integer productId, ProductDto productDto) {
        ProductEntity currentProduct = productRepository.findById(productId).orElseThrow(()-> new InstanceUndefinedException(new Error("Product has not been found")));

        ProductEntity productEntity = tempConverter.dtoToEntity(productDto);
        productEntity.setCreateTime(currentProduct.getCreateTime());
        productEntity.setId(currentProduct.getId());
        ProductEntity updateProduct = productRepository.saveAndFlush(productEntity);

        return tempConverter.entityToDto(updateProduct);
    }

    @Override
    public void deleteProduct(Integer productId) {
        ProductDto productDto = getProductById(productId);
        productRepository.deleteById(productDto.getId());
        productRepository.flush();
    }

    @Override
    public List<ProductDto> listAllByCategory(Integer categoryId) {
        List<ProductDto> returnValue = new ArrayList<>();
        categoryService.getCategoryById(categoryId);
        List<ProductEntity> allProducts = productRepository.findAllByCategoryId(categoryId);
        for(ProductEntity product:allProducts){
            returnValue.add(tempConverter.entityToDto(product));
        }
        return returnValue;
    }
}
