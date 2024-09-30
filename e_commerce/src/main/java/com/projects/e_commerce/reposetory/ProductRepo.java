package com.projects.e_commerce.reposetory;

import com.projects.e_commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(Long category_id);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryIdAndBrand(Long categoryId, String brand);

//    List<Product> findByCategoryIdAndName(Long categoryId, String name);

//    List<Product> findByName(String name);


    List<Product> findByPriceBetweenOrderByPriceAsc(BigDecimal min, BigDecimal max);

    List<Product> findByRatingGreaterThanEqualOrderByRatingDesc(double rate);

    List<Product> findByNameContainingIgnoreCase(String name);
}
