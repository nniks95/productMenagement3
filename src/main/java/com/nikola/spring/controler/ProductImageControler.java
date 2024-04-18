package com.nikola.spring.controler;


import com.nikola.spring.dto.ProductImageDto;
import com.nikola.spring.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/images")
public class ProductImageControler {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping(value = "/getImages")
    public ResponseEntity<List<ProductImageDto>> allImages(){
        return new ResponseEntity<>(productImageService.getAllImages(), HttpStatus.OK);
    }

    @PostMapping(value ="/addImage/{productId}")
    public ResponseEntity<String> addImage(@RequestPart("file") MultipartFile file, @PathVariable("productId")Integer productId){
        String message = "File successfully added";
        productImageService.addImage(productId,file);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }



}
