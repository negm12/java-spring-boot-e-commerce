package com.projects.e_commerce.reposetory;

import com.projects.e_commerce.entity.Product_image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Product_image_repo extends JpaRepository<Product_image,Long> {
    void deleteAllByProductId(Long id);
}
