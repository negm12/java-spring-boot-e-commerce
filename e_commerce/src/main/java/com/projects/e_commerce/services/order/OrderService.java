package com.projects.e_commerce.services.order;

import com.projects.e_commerce.dto.OrderItemDto;
import com.projects.e_commerce.entity.Order;
import com.projects.e_commerce.entity.OrderItem;
import com.projects.e_commerce.entity.Product;
import com.projects.e_commerce.entity.User;
import com.projects.e_commerce.errors.QuantityOutOfStock;
import com.projects.e_commerce.errors.ResourceNotFound;
import com.projects.e_commerce.reposetory.OrderRepo;
import com.projects.e_commerce.reposetory.ProductRepo;
import com.projects.e_commerce.reposetory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;

    @Transactional
    public Order createOrder(Long userId, List<OrderItemDto> items) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = items.stream().map(itemDto -> {
            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFound("Product not found"));


            int new_Product_qty = product.getQuantity()-itemDto.getQuantity();
            if(new_Product_qty<0){
                throw new QuantityOutOfStock("quantity you have ordered not found in stock");
            }

            product.setQuantity(product.getQuantity());
            productRepo.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setQuantity(itemDto.getQuantity());

            return orderItem;
        }).toList();

        order.setOrderItems(orderItems);
        order.setTotalPrice(order.calculateTotalPrice());

        return orderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFound("Order not found"));
    }

    public List<Order> getOrderByUserId(Long userId) {
        return orderRepo.findByUserId(userId);
    }
}


