package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Category;
import com.ecommerce.ecommerce.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    public void createCategory(Category category){
        categoryRepo.save(category);
    }

    public List<Category> listCategory(){
        return categoryRepo.findAll();
    }

    public void editCategory(int categoryId, Category updatecategory) {
        Category category = categoryRepo.getById(categoryId);
        category.setCategoryName(updatecategory.getCategoryName());
        category.setDescription(updatecategory.getDescription());
        category.setImageUrl(updatecategory.getImageUrl());
        categoryRepo.save(category);
    }

    public boolean findById(int categoryId){
        return categoryRepo.findById(categoryId).isPresent();
    }
}
