package com.nikola.spring.controler;

import com.nikola.spring.dto.CategoryDto;
import com.nikola.spring.exceptions.DataNotValidatedException;
import com.nikola.spring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryControler {

    @Autowired
    private CategoryService categoryService;


    @GetMapping(value = "/allCategories")
    public ResponseEntity<List<CategoryDto>> listAllCategories(){
        List<CategoryDto> allCategories = categoryService.listAll();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @GetMapping(value = "/categoryDetails/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryDetails(@PathVariable("categoryId") Integer categoryId){

        CategoryDto category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PutMapping(value = "/updateCategory/{categoryId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategoryDetails(@Validated @RequestBody CategoryDto category, Errors errors, @PathVariable("categoryId") Integer categoryId){
        if(errors.hasErrors()){
            Error error = new Error("Category validation not passed");
            throw new DataNotValidatedException(error);
        }
        CategoryDto updatedCategory = categoryService.updateCategoryById(categoryId, category);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @PostMapping(value = "/saveCategory")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDto> saveCategory(@Validated @RequestBody CategoryDto category, Errors errors){
        if(errors.hasErrors()){
            Error error = new Error("Category validation not passed");
            throw new DataNotValidatedException(error);
        }

        CategoryDto categoryDto = categoryService.addCategory(category);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteCategory/{categoryId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId")Integer categoryId){
        categoryService.deleteCategory(categoryId);
        String message = "Category with ID :" +categoryId+ " is deleted";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

}
