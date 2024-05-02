package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.ProductImageDto;
import com.nikola.spring.entities.ProductImageEntity;
import com.nikola.spring.exceptions.FileUploadException;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.ProductImageRepository;
import com.nikola.spring.service.ProductImageService;
import com.nikola.spring.service.ProductService;
import com.nikola.spring.utils.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private TempConverter tempConverter;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductService productsService;
    @Autowired
    private FileValidator fileValidator;


    @Override
    public List<ProductImageDto> getAllImages() {
        List<ProductImageDto> returnValue = new ArrayList<>();
        List<ProductImageEntity> allImages = productImageRepository.findAll();
        for(ProductImageEntity productImage:allImages){
            returnValue.add(tempConverter.entityToDto(productImage));
        }
        return returnValue;
    }

    @Override
    public ProductImageDto addImage(Integer productId, MultipartFile file) {
        productsService.getProductById(productId);
        fileValidator.validateFile(file);
        ProductImageDto image = getImageByProductId(productId);
        deleteImage(image.getProduct_id());
        try{
            ProductImageDto productImageDto = new ProductImageDto();
            productImageDto.setProduct_id(productId);
            productImageDto.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            //koristi se da se uzme od fajla originalno ime
            productImageDto.setContentType(file.getContentType());
            productImageDto.setSize(file.getSize());
            productImageDto.setData(file.getBytes());
            ProductImageEntity productImageEntity = tempConverter.dtoToEntity(productImageDto);
            ProductImageEntity storedProductImage = productImageRepository.save(productImageEntity);
            return tempConverter.entityToDto(storedProductImage);
        }catch (Exception e){
            throw new FileUploadException(new Error("File upload failed "+ e.getMessage()));
        }
    }

    @Override
    public void deleteImage(Integer productId) {
        Optional<ProductImageEntity> productImageEntityOptional = productImageRepository.findByProductId(productId);
        if(productImageEntityOptional.isPresent()){
            productImageRepository.deleteById(productImageEntityOptional.get().getId());
            productImageRepository.flush();
        }else{
            throw new InstanceUndefinedException(new Error("Product image not exist"));
        }
    }


    @Override
    public ProductImageDto getImageByProductId(Integer productId) {
        ProductImageDto returnValue = null;
        Optional<ProductImageEntity> productImageOptional = productImageRepository.findByProductId(productId);
        if(productImageOptional.isPresent()){
            returnValue = tempConverter.entityToDto(productImageOptional.get());
        }
        return returnValue;
    }
}
