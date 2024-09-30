package com.projects.e_commerce.request;

import com.projects.e_commerce.entity.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
@Data
public class UpdateProductRequest {

    private String name ;
    private String description;
    private String brand;
    private BigDecimal price;
    private Number sale;
    private int quantity;
    private Long category;
    private String[] images ;
}
