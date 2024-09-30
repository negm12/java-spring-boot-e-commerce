package com.projects.e_commerce.services.product;


import com.projects.e_commerce.entity.Category;
import com.projects.e_commerce.entity.Product;
import com.projects.e_commerce.entity.Product_image;
import com.projects.e_commerce.errors.ResourceNotFound;
import com.projects.e_commerce.reposetory.Category_repo;
import com.projects.e_commerce.reposetory.ProductRepo;
import com.projects.e_commerce.reposetory.Product_image_repo;
import com.projects.e_commerce.request.AddProductRequest;
import com.projects.e_commerce.request.UpdateProductRequest;
import com.projects.e_commerce.services.category.Category_service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class product_service implements Iproducts_service {
    
    private final ProductRepo productRepo;
    private  final Category_repo categoryRepo;
    private final Category_service categoryService;
    private final Product_image_repo productImageRepo;


    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(()->new ResourceNotFound("this product not found"));
    }

    @Override
    @Transactional
    public Product updateProduct(UpdateProductRequest product, Long id) throws IOException {

        List<Product_image> imageList = new ArrayList<>();
        for (String imgUrl : product.getImages()) {
            Product_image image = new Product_image( imgUrl);
            imageList.add(image);
        }

        return  Optional.ofNullable(getProductById(id)).map(old_prod->{
            categoryRepo.findById(product.getCategory()).ifPresentOrElse(
                category -> {
                    old_prod.setName(product.getName());
                    old_prod.setPrice(product.getPrice());
                    old_prod.setDescription(product.getDescription());
                    old_prod.setBrand(product.getBrand());
                    old_prod.setQuantity(product.getQuantity());
                    old_prod.setSale(product.getSale());
                    old_prod.setCategory(category);

                    productImageRepo.deleteAllByProductId(old_prod.getId());
//                    old_prod.

                    for (Product_image image : imageList) {
                        image.setProduct(old_prod);  // Link the image to the product
                    }
                    old_prod.setImages(imageList);
                }
                ,
                ()->{
                    throw new ResourceNotFound("category not found");
                }
            );
            return productRepo.save(old_prod);
        }).orElseThrow(()->new ResourceNotFound("product not found"));
    }



    @Override
    public Product insertProduct(AddProductRequest product) throws IOException {

        List<Product_image> imageList = new ArrayList<>();
        for (String imgUrl : product.getImages()) {
            Product_image image = new Product_image(imgUrl);
            imageList.add(image);
        }

        Product prod = new Product();
        categoryRepo.findById(product.getCategory()).ifPresentOrElse(
            category -> {
                prod.setName(product.getName());
                prod.setPrice(product.getPrice());
                prod.setDescription(product.getDescription());
                prod.setBrand(product.getBrand());
                prod.setQuantity(product.getQuantity());
                prod.setCategory(category);

                for (Product_image image : imageList) {
                    image.setProduct(prod);  // Link the image to the product
                }

                prod.setImages(imageList);
            }

            ,
            ()->{
                throw new ResourceNotFound("category not found");
            }
        );

       return productRepo.save(prod);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.findById(id).ifPresentOrElse(productRepo::delete,()->{
            throw new ResourceNotFound("this product not found");
        });
    }

    @Override
    public List<Product> getProductsByCategory(Long category_id) {

        return productRepo.findByCategoryId(category_id);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(Long category_id, String brand) {
        return productRepo.findByCategoryIdAndBrand(category_id,brand);
    }

    @Override
    public List<Product> getProductsByPriceOrderByPriceAsc(BigDecimal min, BigDecimal max) {
        return productRepo.findByPriceBetweenOrderByPriceAsc(min , max);
    }

    @Override
    public List<Product> getProductsByRatingOrderByRatingDesc(double rate) {
        return productRepo.findByRatingGreaterThanEqualOrderByRatingDesc(rate);
    }


    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByNameContainingIgnoreCase(name);
    }
}
