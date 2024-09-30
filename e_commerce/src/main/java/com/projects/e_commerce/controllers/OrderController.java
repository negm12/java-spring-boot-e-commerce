package com.projects.e_commerce.controllers;

import com.projects.e_commerce.dto.OrderItemDto;
import com.projects.e_commerce.entity.Order;
import com.projects.e_commerce.errors.QuantityOutOfStock;
import com.projects.e_commerce.errors.ResourceNotFound;
import com.projects.e_commerce.response.ApiResponse;
import com.projects.e_commerce.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId,
                                                   @RequestBody List<OrderItemDto> items) {

        try {
            Order createdOrder = orderService.createOrder(userId, items);
            return ResponseEntity.ok(new ApiResponse("order added success",createdOrder));
        } catch (ResourceNotFound | QuantityOutOfStock e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse("all orders",orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(new ApiResponse("success",order));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrderByUserId(userId);
            return ResponseEntity.ok(new ApiResponse("success",orders));
        } catch (RuntimeException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
}

