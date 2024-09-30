package com.projects.e_commerce.request;

import com.projects.e_commerce.entity.Category;
import com.projects.e_commerce.entity.Product_image;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddProductRequest {
    private String name ;
    private String description;
    private String brand;
    private BigDecimal price;
    private Number sale=0;
    private int quantity;
    private Long category;
    private String[] images ;
}
