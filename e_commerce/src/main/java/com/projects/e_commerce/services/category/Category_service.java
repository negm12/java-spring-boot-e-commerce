package com.projects.e_commerce.services.category;

import com.projects.e_commerce.entity.Category;
import com.projects.e_commerce.errors.AlreadyExistRecord;
import com.projects.e_commerce.errors.ResourceNotFound;
import com.projects.e_commerce.reposetory.Category_repo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Category_service implements Icategory_service {
    private final Category_repo categoryRepo;


    public List<Category> getAllCategories(){
         List<Category> categories =categoryRepo.findAll();
         return categories;
    }

    public Category getCategoryById (Long id){
        Category category = categoryRepo.findById(id)
                .orElseThrow(()->new ResourceNotFound("not found category belond to this id "+id));

        return category;

    }

    public Category insertCategory(Category category){

        return Optional.ofNullable(category).filter(cat->!categoryRepo.existsByName(category.getName()))
                .map(categoryRepo::save)
                .orElseThrow(()->new AlreadyExistRecord("this category already exist "));
    }

    public Category updateCategory(Category category,Long id){
        return Optional.ofNullable(getCategoryById(id)).map(old_cat->{
            old_cat.setName(category.getName());
            old_cat.setImage(category.getImage());
            return categoryRepo.save(old_cat);
        }).orElseThrow(()->  new ResourceNotFound("category not found "));

    }

    public void deleteCategory (Long id){
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo :: delete,()->{
            throw new ResourceNotFound("category not found");
        });

//        categoryRepo.findById(id).ifPresentOrElse(category -> categoryRepo.delete(category),()->{
//            throw new ResourecNotFound("category not found");
//        });
    };


}
