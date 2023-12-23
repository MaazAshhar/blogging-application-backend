package com.ashhar.blogappapis.controllers;


import com.ashhar.blogappapis.payloads.ApiResponse;
import com.ashhar.blogappapis.payloads.CategoryDto;
import com.ashhar.blogappapis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;


@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategory=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("catId") String uid){
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,uid);
        return new ResponseEntity<>(updatedCategory,HttpStatus.ACCEPTED);
    }

    //delete
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") String uid){
        this.categoryService.deleteCategory(uid);
        String message= MessageFormat.format("Category associated to id {0} deleted successfully",uid);
        ApiResponse apiResponse=new ApiResponse(message,true);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    //get by id
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("catId") String uid){
        CategoryDto categoryDto=this.categoryService.getCategoryById(uid);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    // get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtos=this.categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }

}
