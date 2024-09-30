package com.projects.e_commerce.controllers;


import com.projects.e_commerce.entity.Category;
import com.projects.e_commerce.errors.AlreadyExistRecord;
import com.projects.e_commerce.errors.ResourceNotFound;
import com.projects.e_commerce.response.ApiResponse;
import com.projects.e_commerce.services.category.Category_service;
import com.projects.e_commerce.services.category.Icategory_service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
private final Icategory_service categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCats(){
        try {
            return ResponseEntity.ok(new ApiResponse("get categories success",
                    categoryService.getAllCategories()));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCatById(@PathVariable Long id){

        try {
//            Category category = categoryService.getCategoryById(id);
//            if(category != null){
                return ResponseEntity.ok(new ApiResponse("category get success",
                        categoryService.getCategoryById(id)));
//            }
        }catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),e));
        }
//        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
//                .body(new ApiResponse("inernal server error ",INTERNAL_SERVER_ERROR));

    }

    @PostMapping
    public ResponseEntity<ApiResponse>  insertCategory (@RequestBody Category category){
        try {
            return ResponseEntity.ok(new ApiResponse("category added success",
                    categoryService.insertCategory(category)));

        }catch (AlreadyExistRecord e){
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(),CONFLICT));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory (@RequestBody Category category,@PathVariable Long id){
        try {
            Category category1 = categoryService.getCategoryById(id);
            if (category1 != null) {
                return ResponseEntity.ok(new ApiResponse("category updated successfully ",
                        categoryService.updateCategory(category, id)));
            }
        }catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("internal server error",INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory (@PathVariable Long id){
        try {

            Category category = categoryService.getCategoryById(id);
            if(category != null){
                categoryService.deleteCategory(id);
                return ResponseEntity.ok(new ApiResponse("category deleted successfully", null));

            }
        }catch (ResourceNotFound e){

            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("internal server error",INTERNAL_SERVER_ERROR));

    }

}
