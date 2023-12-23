package com.ashhar.blogappapis.services.impl;

import com.ashhar.blogappapis.entities.Category;
import com.ashhar.blogappapis.exceptions.IllegalUUIDException;
import com.ashhar.blogappapis.exceptions.ResourceNotFoundException;
import com.ashhar.blogappapis.payloads.CategoryDto;
import com.ashhar.blogappapis.repositories.CategoryRepo;
import com.ashhar.blogappapis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    //create
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.modelMapper.map(categoryDto,Category.class);
        Category savedCategory=categoryRepo.save(category);
        return this.modelMapper.map(savedCategory,CategoryDto.class);
    }

    //update
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
    	UUID cId = null;
        try {
            cId = UUID.fromString(categoryId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
    	UUID finalcId=cId;
        Category category=this.categoryRepo.findById(finalcId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",finalcId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory=this.categoryRepo.save(category);
        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }

    //get category by Id
    @Override
    public CategoryDto getCategoryById(String categoryId) {
    	UUID cId = null;
        try {
            cId = UUID.fromString(categoryId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
    	UUID finalcId=cId;
        Category category=this.categoryRepo.findById(finalcId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",finalcId));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    // get all categories
    @Override
    public List<CategoryDto> getAllCategory() {
        List<CategoryDto> categoryDtos=new ArrayList<>();
        List<Category> categories=this.categoryRepo.findAll();
        categories.forEach((category)->{
            categoryDtos.add(this.modelMapper.map(category,CategoryDto.class));
        });
        return categoryDtos;
    }

    //delete category.
    @Override
    public void deleteCategory(String categoryId) {
    	UUID cId = null;
        try {
            cId = UUID.fromString(categoryId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
    	UUID finalcId=cId;
        Category category=this.categoryRepo.findById(finalcId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",finalcId));
        this.categoryRepo.delete(category);
    }
}
