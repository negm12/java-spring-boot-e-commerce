package com.projects.e_commerce.controllers;

import com.projects.e_commerce.entity.Review;
import com.projects.e_commerce.request.AddReviewRequest;
import com.projects.e_commerce.response.ApiResponse;
import com.projects.e_commerce.services.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {


    private final ReviewService reviewService;

    // Add a review
    @PostMapping
    public ResponseEntity<ApiResponse> addReview(@RequestBody AddReviewRequest request) {
        try {
            Review review = reviewService.addReview(request);
            return  ResponseEntity.ok(new ApiResponse("review addedd success",review));
        } catch (RuntimeException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

}

