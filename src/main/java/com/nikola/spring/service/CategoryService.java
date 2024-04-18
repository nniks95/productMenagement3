package com.nikola.spring.service;

import com.nikola.spring.dto.CategoryDto;

import java.util.List;

public interface    CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    List<CategoryDto> listAll();

    CategoryDto getCategoryById(Integer categoryId);

    CategoryDto updateCategoryById(Integer categoryId, CategoryDto categoryDto);

    void deleteCategory(Integer categoryId);






}
