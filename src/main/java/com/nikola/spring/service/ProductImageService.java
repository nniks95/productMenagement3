package com.nikola.spring.service;

import com.nikola.spring.dto.ProductDto;
import com.nikola.spring.dto.ProductImageDto;
import com.nikola.spring.entities.ProductImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {

    List<ProductImageDto> getAllImages();

    ProductImageDto addImage(Integer productId, MultipartFile file);

    void deleteImage(Integer productId);

    ProductImageDto getImageByProductId(Integer productId);

}
