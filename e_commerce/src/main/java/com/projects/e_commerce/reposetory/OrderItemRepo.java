package com.projects.e_commerce.reposetory;

import com.projects.e_commerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
