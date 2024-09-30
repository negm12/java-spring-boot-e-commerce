package com.projects.e_commerce.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @Column(nullable = false)
    private String name ;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private BigDecimal price;

    private Number sale=0;


    @Column(nullable = false)
    private int quantity;


    private double rating=0;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Product_image> images;

    public Product(String name, String description, String brand, BigDecimal price, Number sale, int quantity, Category category, List<Product_image> images) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.sale = sale;
        this.quantity = quantity;
        this.category = category;
        this.images = images;
    }
}
