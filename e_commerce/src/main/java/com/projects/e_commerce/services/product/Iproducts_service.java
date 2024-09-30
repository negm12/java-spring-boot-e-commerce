package com.projects.e_commerce.services.product;

import com.projects.e_commerce.entity.Product;
import com.projects.e_commerce.request.AddProductRequest;
import com.projects.e_commerce.request.UpdateProductRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface Iproducts_service {
    public List<Product> getAllProducts();
    public Product getProductById(Long id);
    public Product updateProduct(UpdateProductRequest product, Long id) throws IOException;
    public Product insertProduct(AddProductRequest product) throws IOException;
    public void deleteProduct(Long id);


    public List<Product> getProductsByCategory(Long category_id);
    public List<Product> getProductsByBrand(String brand);
    public List<Product> getProductsByCategoryAndBrand(Long category_id,String brand);
    public List<Product> getProductsByPriceOrderByPriceAsc(BigDecimal min , BigDecimal max);
    public List<Product> getProductsByRatingOrderByRatingDesc(double rate);
//    public List<Product> getProductsByName(String name);

    List<Product> getProductsByName(String name);
//
//    public List<Product> getProductsOrderBySale()




}
