package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CartItemDto;
import com.nikola.spring.dto.ProductDto;
import com.nikola.spring.entities.ProductEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CartItemRepository;
import com.nikola.spring.repositories.ProductRepository;
import com.nikola.spring.service.CartItemService;
import com.nikola.spring.service.CartService;
import com.nikola.spring.service.CategoryService;
import com.nikola.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private TempConverter tempConverter;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired private CartService cartService;
    @Autowired private CartItemRepository cartItemRepository;


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
        List<CartItemDto> allItems = cartItemService.listAllItemsByProductId(productId);
        System.out.println(allItems.size());
        for(CartItemDto cartItemDto:allItems){
            cartItemDto.setPrice(updateProduct.getPrice()*cartItemDto.getQuantity());
            cartItemRepository.saveAndFlush(tempConverter.dtoToEntity(cartItemDto));
        }
        cartService.refreshAllCarts();
        return tempConverter.entityToDto(updateProduct);
    }

    @Override
    public void deleteProduct(Integer productId) {
        ProductDto productDto = getProductById(productId);
        cartItemService.removeAllByProductId(productId);
        productRepository.deleteById(productDto.getId());
        cartService.refreshAllCarts();
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
