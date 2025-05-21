package com.wipro.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.ordermanagement.entity.OrderEntity;
import com.wipro.ordermanagement.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping
	public OrderEntity placeOrder(@PathVariable Integer userId) {
		
		
		return null;
	}

	@PutMapping
	public OrderEntity cancelOrder(@PathVariable Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping
	public List<OrderEntity> getAllOrders() {

		System.out.println("---------");
		return orderService.getAllOrders();
	}

	@GetMapping("/{userid}")
	public List<OrderEntity> getOrdersByUserId(@PathVariable Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{id}")
	public OrderEntity getOrderById(@PathVariable Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}
}
