package com.nikola.spring.controler;


import com.nikola.spring.dto.ProductDto;
import com.nikola.spring.exceptions.DataNotValidatedException;
import com.nikola.spring.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductControler {

    @Autowired
    private ProductsService productsService;


    @GetMapping(value = "/allProducts")
    public ResponseEntity<List<ProductDto>> listAllProducts(){

        List<ProductDto> allProducts = productsService.listAll();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping(value = "/productDetails/{productId}")
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable("productId") Integer productId){

        ProductDto productDto = productsService.getProductById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @PutMapping(value = "/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateCategoryDetails(@Validated @RequestBody ProductDto product, Errors errors, @PathVariable("productId") Integer productId){

        ProductDto productDto = productsService.updateProductById(productId,product);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    //pathvariable se koristi kada unutar value imas neku odredjenu vrednost unutar {}

    @PostMapping(value = "/saveProduct")
    public ResponseEntity<ProductDto> saveProduct(@Validated @RequestBody ProductDto product, Errors errors){
        if(errors.hasErrors()){
            Error error = new Error("Product validation not passed");
            throw new DataNotValidatedException(error);
        }

        ProductDto productDto = productsService.addProduct(product);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId")Integer productId){
        productsService.deleteProduct(productId);
        String message = "Product with ID: "+productId+" is deleted.";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/allProducts/{categoryId}")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryId(@PathVariable("categoryId")Integer categoryId){
        return new ResponseEntity<>(productsService.listAllByCategory(categoryId),HttpStatus.OK);
    }

}
