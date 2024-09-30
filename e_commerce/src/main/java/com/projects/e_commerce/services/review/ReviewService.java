package com.projects.e_commerce.services.review;

import com.projects.e_commerce.entity.Product;
import com.projects.e_commerce.entity.Review;
import com.projects.e_commerce.entity.User;
import com.projects.e_commerce.reposetory.ProductRepo;
import com.projects.e_commerce.reposetory.ReviewRepo;
import com.projects.e_commerce.reposetory.UserRepository;
import com.projects.e_commerce.request.AddReviewRequest;
import com.projects.e_commerce.response.ApiResponse;
import com.projects.e_commerce.services.product.Iproducts_service;
import com.projects.e_commerce.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Iproducts_service productsService;

    // Add a review
    public Review addReview(AddReviewRequest request) {

            User user = userService.getUserById(request.getUserId());
            Product product = productsService.getProductById(request.getProductID());

            Review review = new Review();
            review.setUser(user);
            review.setProduct(product);
            review.setReviewText(request.getReviewText());
            review.setRating(request.getRating());

            Review savedReview = reviewRepo.save(review);
            Double product_avg_rating = getAverageRatingForProduct(request.getProductID());
            product.setRating(product_avg_rating);
            productRepo.save(product);

            return  savedReview;

    }


    // Get average rating for a product
    public Double getAverageRatingForProduct(Long productId) {
        return reviewRepo.findAverageRatingByProductId(productId);
    }
}

