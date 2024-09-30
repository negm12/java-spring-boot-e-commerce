package com.projects.e_commerce.controllers;

import com.projects.e_commerce.entity.Product;
import com.projects.e_commerce.errors.ResourceNotFound;
import com.projects.e_commerce.request.AddProductRequest;
import com.projects.e_commerce.request.UpdateProductRequest;
import com.projects.e_commerce.response.ApiResponse;
import com.projects.e_commerce.services.product.Iproducts_service;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final Iproducts_service productsService;

    @GetMapping("")
    public ResponseEntity<ApiResponse>  getAllProducts(){

        try {
            return ResponseEntity.ok(new ApiResponse("get all products successfully",
                    productsService.getAllProducts()));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(new ApiResponse("get product successfully",
                    productsService.getProductById(id)));

        }catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id) {
        try {
            Product product = productsService.getProductById(id);
            if(product != null){
                return ResponseEntity.ok(new ApiResponse("product updated success",
                        productsService.updateProduct(request,id)));
            }
        }catch (ResourceNotFound | IOException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("internal server error ",INTERNAL_SERVER_ERROR));

    }


    @PostMapping("")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody AddProductRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse("product addedd successfully",
                    productsService.insertProduct(request)));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            Product product = productsService.getProductById(id);
            if(product != null){
                productsService.deleteProduct(id);
                return ResponseEntity.ok(new ApiResponse("product deleted success", null));
            }
        }catch (ResourceNotFound  e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("internal server error ",INTERNAL_SERVER_ERROR));
    }



    @GetMapping("/by_category/{cat_id}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable Long cat_id){
        try {
            List<Product> products = productsService.getProductsByCategory(cat_id);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(products.size() + " products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/by_brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand){
        try {
            List<Product> products = productsService.getProductsByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(products.size() + " products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }

    @GetMapping("/by_category/{cat_id}/by_brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable Long cat_id,@PathVariable String brand){
        try {
            List<Product> products = productsService.getProductsByCategoryAndBrand(cat_id,brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(products.size() + " products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }


    @GetMapping("/by_price/{min}/{max}")
    public ResponseEntity<ApiResponse> getProductsByPriceOrderByPriceAsc(@PathVariable BigDecimal min, @PathVariable BigDecimal max){
        try {
            List<Product> products = productsService.getProductsByPriceOrderByPriceAsc(min,max);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(products.size() + " products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }



    @GetMapping("/by_rating/{rate}")
    public ResponseEntity<ApiResponse> getProductsByRatingOrderByRatingDesc(@PathVariable double rate){
        try {
            List<Product> products = productsService.getProductsByRatingOrderByRatingDesc(rate);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(products.size()+" products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }

    @GetMapping("/by_name/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name){
        try {
            List<Product> products = productsService.getProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(products.size() + " products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success",products));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }

    }

}
