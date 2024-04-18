package com.nikola.spring.service.impl;

import com.nikola.spring.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    List<CategoryDto> listAll();

    CategoryDto getCategoryById(Integer categoryId);

    CategoryDto updateCategoryById(Integer categoryId);

    void deleteCategory(Integer categoryId);






}
