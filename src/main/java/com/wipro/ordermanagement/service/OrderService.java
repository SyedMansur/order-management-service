package com.wipro.ordermanagement.service;

import java.util.List;

import com.wipro.ordermanagement.dto.OrderRequest;
import com.wipro.ordermanagement.response.OrderResponse;

public interface OrderService {
	
    String placeOrder(OrderRequest orderReq);
    
    
    String cancelOrder(Integer orderId);
    
//    List<OrderEntity> getAllOrders();
    
    List<OrderResponse> getOrdersByUserId(String userId);
    
//    OrderEntity getOrderById(Integer orderId);
}
