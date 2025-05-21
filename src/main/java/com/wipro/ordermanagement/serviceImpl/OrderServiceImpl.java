package com.wipro.ordermanagement.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wipro.ordermanagement.entity.CartEntity;
import com.wipro.ordermanagement.entity.OrderEntity;
import com.wipro.ordermanagement.repository.CartRepo;
import com.wipro.ordermanagement.repository.OrderRepo;
import com.wipro.ordermanagement.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CartRepo cartRepo;

	@Override
	public OrderEntity placeOrder(Integer userId) {
		return null;
//
//        CartEntity cart = cartRepo.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));
//        OrderEntity order = new Order();
//        order.setUserId(userId);
//        order.setProdDetails(new HashMap<>(cart.getProdDetails()));
//        order.setTotalQty(cart.getTotalQty());
//        order.setTotalPrice(cart.getTotalPrice());
//        order.setStatus("PLACED");
//        order.setOrderDate(LocalDateTime.now());
//        // Update Product Quantities in Product Service
//        for (Map.Entry<Integer, Integer> entry : cart.getProdDetails().entrySet()) {
//            Integer productId = entry.getKey();
//            Integer quantity = entry.getValue();
//            ProductDto product = restTemplate.getForObject(PRODUCT_SERVICE_URL + productId, ProductDto.class);
//            if (product == null) {
//                throw new RuntimeException("Product not found with id: " + productId);
//            }
//            if (product.getAvailableQty() < quantity) {
//                throw new RuntimeException("Not enough quantity for product id: " + productId);
//            }
//            // Reduce product quantity
//            product.setAvailableQty(product.getAvailableQty() - quantity);
//            restTemplate.put(PRODUCT_SERVICE_URL + "update/" + productId, product);
//        }
//        // Save the order
//        Order savedOrder = orderRepository.save(order);
//        // After placing order, you can clear the cart if required
//        cartRepository.delete(cart);
//        return savedOrder;
//		return null;
	}

	@Override
	public OrderEntity cancelOrder(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderEntity> getAllOrders() {

		return orderRepo.findAll();
	}

	@Override
	public List<OrderEntity> getOrdersByUserId(Integer userId) {

		return null;
	}

	@Override
	public OrderEntity getOrderById(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
