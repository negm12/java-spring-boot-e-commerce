package com.projects.e_commerce.reposetory;

import com.projects.e_commerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}