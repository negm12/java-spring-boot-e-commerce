package com.projects.e_commerce.services.category;

import com.projects.e_commerce.entity.Category;

import java.util.List;

public interface Icategory_service {

    public List<Category> getAllCategories();
    public Category getCategoryById(Long id);
    public Category insertCategory (Category category);
    public Category updateCategory (Category category,Long id);
    public void deleteCategory(Long id);
}
