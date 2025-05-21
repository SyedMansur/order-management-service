package com.wipro.ordermanagement.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wipro.ordermanagement.dto.OrderRequest;
import com.wipro.ordermanagement.dto.ProductDetails;
import com.wipro.ordermanagement.dto.ProductDto;
import com.wipro.ordermanagement.entity.OrderEntity;
import com.wipro.ordermanagement.repository.CartRepo;
import com.wipro.ordermanagement.repository.OrderRepo;
import com.wipro.ordermanagement.response.OrderResponse;
import com.wipro.ordermanagement.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	CartRepo cartRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OrderRepo orderRepo;

	@Override
	public String placeOrder(OrderRequest orderRequest) {
		String userId = orderRequest.getUserId();
		Map<Integer, Integer> productQty = orderRequest.getProductQty();

		if (userId == null || productQty.isEmpty()) {
			return "Invalid Order Request!";
		}

		double totalPrice = 0;
		int totalQuantity = 0;

		// Check product availability
		for (Map.Entry<Integer, Integer> entry : productQty.entrySet()) {
			Integer productId = entry.getKey();
			Integer quantityRequested = entry.getValue();

			String productMsUrl = "http://localhost:8080/product/" + productId;
			ResponseEntity<ProductDto> response = restTemplate.getForEntity(productMsUrl, ProductDto.class);

			if (!response.getStatusCode().is2xxSuccessful()) {
				return "Product Service Error!";
			}

			ProductDto product = response.getBody();

			if (product.getProductQuantity() < quantityRequested) {
				return "Insufficient Stock for Product ID: " + productId;
			}

			totalPrice += product.getTotProdPrice() * quantityRequested;
			totalQuantity += quantityRequested;
		}

		// Save order details
		OrderEntity order = new OrderEntity();
		order.setUserId(userId);
		order.setProdDetails(productQty);
		order.setTotalPrice(totalPrice);
		order.setTotalQty(totalQuantity);
		order.setStatus("PLACED");
		order.setOrderDate(LocalDateTime.now());

		orderRepo.save(order);

		return "Order placed successfully!";
	}

	@Override
	public String cancelOrder(Integer orderId) {
		Optional<OrderEntity> orderOpt = orderRepo.findById(orderId);

		if (orderOpt.isEmpty()) {
			return "Order not found!";
		}

		OrderEntity order = orderOpt.get();

		// Check if order can be canceled
		if (!order.getStatus().equals("PLACED")) {
			return "Order cannot be canceled!";
		}

		Map<Integer, Integer> productQty = order.getProdDetails();

		for (Map.Entry<Integer, Integer> entry : productQty.entrySet()) {
			Integer productId = entry.getKey();
			Integer canceledQuantity = entry.getValue();

			// Fetch current product details (optional)
			String getProductUrl = "http://localhost:8080/product/" + productId;
			ResponseEntity<ProductDetails> productResponse = restTemplate.getForEntity(getProductUrl,
					ProductDetails.class);

			if (!productResponse.getStatusCode().is2xxSuccessful()) {
				return "Failed to fetch product details for Product ID: " + productId;
			}

			ProductDetails productDetails = productResponse.getBody();
			productDetails.setProductQuantity(productDetails.getProductQuantity() + canceledQuantity); // Restore stock

			// Call update API
			String updateUrl = "http://localhost:8080/product/" + productId;
			restTemplate.put(updateUrl, productDetails);
		}

		// Update order status
		order.setStatus("CANCELED");
		orderRepo.save(order);

		return "Order canceled successfully and stock restored!";
	}

//	@Override
//	public List<OrderEntity> getAllOrders() {
//
//		return orderRepo.findAll();
//	}

	@Override
	public List<OrderResponse> getOrdersByUserId(String userId) {

		List<OrderResponse> orderResponseList = new ArrayList<>();

		List<OrderEntity> ordersList = orderRepo.findByUserId(userId);
		for (OrderEntity order : ordersList) {

			OrderResponse orderResponse = new OrderResponse();
			orderResponse.setTotalQuantity(order.getTotalQty());
			orderResponse.setTotalPrice(order.getTotalPrice());
			orderResponse.setStatus(order.getStatus());
			orderResponse.setOrderId(order.getId());

			Map<Integer, Integer> productQty = order.getProdDetails();

			for (Map.Entry<Integer, Integer> entry : productQty.entrySet()) {
				Integer productId = entry.getKey();
				String updateUrl = "http://localhost:8080/product/" + productId;
				ResponseEntity<ProductDto> productResponse = restTemplate.getForEntity(updateUrl, ProductDto.class);
				ProductDto product = productResponse.getBody();

				orderResponse.setPtoductName(product.getProductName());
				orderResponse.setImageURL(product.getImageURL());
				orderResponse.setProductDesc(product.getProductDesc());
			}
			orderResponseList.add(orderResponse);
		}
		return orderResponseList;
	}
}
