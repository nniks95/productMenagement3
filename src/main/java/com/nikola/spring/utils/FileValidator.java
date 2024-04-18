package com.nikola.spring.utils;

import com.nikola.spring.exceptions.DataNotValidatedException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Component
public class FileValidator {

    public boolean validateFile(MultipartFile file){
        boolean returnValue = false;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(isSuportedExtension(extension)){
            returnValue = true;
        }else{
            throw new DataNotValidatedException(new Error("File is not valid"));
        }
        return returnValue;
    }

    public boolean isSuportedExtension(String extension){
        boolean returnValue = false;
        Optional<String> extensionOptional = Optional.ofNullable(extension);
        if(extensionOptional.isPresent()){
            if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg")){
                returnValue = true;
            }
        }
        return returnValue;
    }





}
