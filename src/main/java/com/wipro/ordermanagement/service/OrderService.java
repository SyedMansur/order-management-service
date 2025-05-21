package com.wipro.ordermanagement.service;

import java.util.List;

import com.wipro.ordermanagement.entity.OrderEntity;

public interface OrderService {
	
    OrderEntity placeOrder(Integer userId);
    
    
    OrderEntity cancelOrder(Integer orderId);
    
    List<OrderEntity> getAllOrders();
    
    List<OrderEntity> getOrdersByUserId(Integer userId);
    
    OrderEntity getOrderById(Integer orderId);
}
