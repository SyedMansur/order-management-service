package com.wipro.ordermanagement.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.ordermanagement.dto.OrderRequest;
import com.wipro.ordermanagement.response.OrderResponse;
import com.wipro.ordermanagement.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping
	public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderReq) {
		
		String s = orderService.placeOrder(orderReq);
		return ResponseEntity.ok(s);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable int orderId) {
		
		orderService.cancelOrder(orderId);
		return ResponseEntity.ok("Order cancelled");
	}

//	@GetMapping
//	public List<OrderEntity> getAllOrders() {
//
//		System.out.println("---------");
//		return orderService.getAllOrders();
//	}

	@GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable String userId) {
        List<OrderResponse> orders = orderService.getOrdersByUserId(userId);
        
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        
        return ResponseEntity.ok(orders);
    }

//	@GetMapping("/{id}")
//	public OrderEntity getOrderById(@PathVariable Integer orderId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
