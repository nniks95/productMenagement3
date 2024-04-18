package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CategoryDto;
import com.nikola.spring.entities.CategoryEntity;
import com.nikola.spring.exceptions.DuplicateNotAllowed;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CategoryRepository;
import com.nikola.spring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private TempConverter tempConverter;
    @Autowired
    private CategoryRepository categoryRepository;





    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findByName(categoryDto.getName());
        if(categoryOptional.isPresent()){
            throw new DuplicateNotAllowed(new Error("Category with this name exist"));
        }
        CategoryEntity categoryEntity = tempConverter.dtoToEntity(categoryDto);
        CategoryEntity storedCategory = categoryRepository.save(categoryEntity);
        return tempConverter.entityToDto(storedCategory);
    }

    @Override
    public List<CategoryDto> listAll() {

        List<CategoryDto> returnValue = new ArrayList<>();
        List<CategoryEntity> allCategories = categoryRepository.findAll();

        for(CategoryEntity categoryEntity: allCategories){
            CategoryDto categoryDto = tempConverter.entityToDto(categoryEntity);
            returnValue.add(categoryDto);
        }
        return returnValue;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        CategoryDto returnValue = null;
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()){
            CategoryEntity categoryEntity = categoryOptional.get();
            returnValue = tempConverter.entityToDto(categoryEntity);
        }else{
            throw new InstanceUndefinedException(new Error("Category undefined"));
        }
        return returnValue;
    }

    @Override
    public CategoryDto updateCategoryById(Integer categoryId, CategoryDto categoryDto) {
        CategoryDto currentCategory = getCategoryById(categoryId);
        categoryDto.setId(currentCategory.getId());

        Optional<CategoryEntity> categoryOptional = categoryRepository.findByName(categoryDto.getName());
        if(categoryOptional.isPresent()){
            if(categoryOptional.get().getId() != categoryId){
                throw new DuplicateNotAllowed(new Error("Category duplicate not allowed"));
            }
        }
        CategoryEntity categoryEntity = tempConverter.dtoToEntity(categoryDto);
        CategoryEntity updateCategory = categoryRepository.saveAndFlush(categoryEntity);
        return tempConverter.entityToDto(updateCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        CategoryDto categoryDto = getCategoryById(categoryId);
        categoryRepository.deleteById(categoryDto.getId());
        categoryRepository.flush();
    }
}
