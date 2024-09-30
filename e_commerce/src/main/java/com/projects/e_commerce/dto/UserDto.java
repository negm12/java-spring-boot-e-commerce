package com.projects.e_commerce.dto;

import com.projects.e_commerce.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Order> orders;
}
