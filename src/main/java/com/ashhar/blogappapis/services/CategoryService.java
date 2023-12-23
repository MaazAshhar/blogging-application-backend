package com.ashhar.blogappapis.services;

import com.ashhar.blogappapis.payloads.CategoryDto;

import java.util.List;


public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);
    public CategoryDto getCategoryById(String categoryId);
    public List<CategoryDto> getAllCategory();
    public void deleteCategory(String categoryId);
}
