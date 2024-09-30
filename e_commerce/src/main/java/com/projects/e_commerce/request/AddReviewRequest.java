package com.projects.e_commerce.request;

import lombok.Data;

@Data
public class AddReviewRequest {

    private Long userId;

    private Long productID;

    private String reviewText;

    private int rating;
}
