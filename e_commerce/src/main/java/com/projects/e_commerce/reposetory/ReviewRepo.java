package com.projects.e_commerce.reposetory;

import com.projects.e_commerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    // Custom query to calculate the average rating for a product
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double findAverageRatingByProductId(@Param("productId") Long productId);

    // Find all reviews for a product
    List<Review> findByProductId(Long productId);
}
