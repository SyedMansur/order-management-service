package com.wipro.ordermanagement.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wipro.ordermanagement.dto.CartDto;
import com.wipro.ordermanagement.dto.ProductDto;
import com.wipro.ordermanagement.entity.CartEntity;
import com.wipro.ordermanagement.repository.CartRepo;
import com.wipro.ordermanagement.response.CartResponse;
import com.wipro.ordermanagement.service.CartService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CartRepo cartRepo;

	@Override
	public CartEntity addProductToCart(CartDto cartItem) {
		double totalPrice = 0;
		int totalQty = 0;
		CartEntity cartEntity = new CartEntity();
		for (Map.Entry<Integer, Integer> entry : cartItem.getProductQty().entrySet()) {
			CartEntity cart = cartRepo.findEntity(cartItem.getUserId(), entry.getKey());
			if (cart == null) {
				ProductDto productResponse = getProductById(entry.getKey());
				if (productResponse != null && productResponse.getProductQuantity() >= entry.getValue()) {
					totalPrice += entry.getValue() * productResponse.getProductPrice();
					totalQty += entry.getValue();
				} else {
					throw new RuntimeException("Product " + entry.getKey() + " not available in required quantity");
				}
			} else {
				ProductDto productResponse = restTemplate
						.getForObject("http://localhost:8080/product/" + entry.getKey(), ProductDto.class);
				if (productResponse != null && productResponse.getProductQuantity() >= entry.getValue()) {
					totalPrice += entry.getValue() * productResponse.getProductPrice() + cart.getTotalPrice();
					totalQty += entry.getValue() + cart.getTotalQuantity();
				} else {
					throw new RuntimeException("Product " + entry.getKey() + " not available in required quantity");
				}
				cart.setTotalPrice(totalPrice);
				cart.setTotalQuantity(totalQty);

				for (Map.Entry<Integer, Integer> cartQty : cart.getProductQty().entrySet()) {

					Integer prodQty = cartQty.getValue() + entry.getValue();
					Map<Integer, Integer> map = new HashMap<>();
					map.put(entry.getKey(), prodQty);
					cart.setProductQty(map);
				}
				return cartRepo.save(cart);
			}
		}
		cartEntity.setUserId(cartItem.getUserId());
		cartEntity.setProductQty(cartItem.getProductQty());
		cartEntity.setTotalPrice(totalPrice);
		cartEntity.setTotalQuantity(totalQty);
		return cartRepo.save(cartEntity);
	}

	@Override
	public void deleteProductFromCart(int cartId, int productId) {

		Optional<CartEntity> cartEntity = cartRepo.findById(cartId);
		try {
			// commented for testing
//			if (!cartEntity.isPresent()) {
//				throw new RuntimeException("Cart not found");
//			}
			CartEntity cart = cartEntity.get();
			if (cartEntity.isPresent() && cart.getProductQty().containsKey(productId)) {
				cartRepo.deleteById(cartId);
			}
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
			throw new RuntimeException("Cart not found");
		}
	}

	@Override
	public CartEntity updateProductQty(CartDto cartItem) {
		double totalPrice = 0;
		int totalQty = 0;
		CartEntity findCart = null;
		
		Map<Integer, Integer> map = cartItem.getProductQty();
		for(Integer key : map.keySet()) {
			findCart = cartRepo.findEntity(cartItem.getUserId(), key);
		}
		if (findCart != null) {
			for (Map.Entry<Integer, Integer> entry : cartItem.getProductQty().entrySet()) {
				CartEntity cart = cartRepo.findEntity(cartItem.getUserId(), entry.getKey());
				if (cart != null) {
					ProductDto productResponse = restTemplate
							.getForObject("http://localhost:8080/product/" + entry.getKey(), ProductDto.class);
					if (productResponse != null && productResponse.getProductQuantity() >= entry.getValue()) {
						totalPrice += entry.getValue() * productResponse.getProductPrice();
						totalQty += entry.getValue();
						cart.setTotalPrice(totalPrice);
						cart.setTotalQuantity(totalQty);
						cart.setProductQty(cartItem.getProductQty());
						
						return cartRepo.save(cart);
					} else {
						throw new RuntimeException("Product " + entry.getKey() + " not available in required quantity");
					}
				}
			}
		} else {
			throw new RuntimeException("Cart not found");
		}
		return null;
	}

	@Override
	public List<CartResponse> getCartByUserId(String userId) {
		
		List<CartResponse> cartResList = new ArrayList<>();
		List<CartEntity> cartList = cartRepo.findByUserId(userId);
		
		for(CartEntity cart : cartList) {
			CartResponse cartRes = new CartResponse();
			
			cartRes.setId(cart.getId());
			cartRes.setProductQty(cart.getProductQty());
			for (Map.Entry<Integer, Integer> entry : cart.getProductQty().entrySet()) {
				
				ProductDto prodDto = getProductById(entry.getKey());
				cartRes.setPtoductName(prodDto.getProductName());
				cartRes.setProductPrice(prodDto.getProductPrice());
				cartRes.setImageURL(prodDto.getImageURL());
				cartRes.setProdCat(prodDto.getProdCat());
				cartRes.setProductDesc(prodDto.getProductDesc());
			}
			cartRes.setTotalPrice(cart.getTotalPrice());
			cartRes.setTotalQuantity(cart.getTotalQuantity());
			cartRes.setUserId(cart.getUserId());
			cartResList.add(cartRes);
		}

		return cartResList;
	}
	
	public ProductDto getProductById(Integer id) {
		
		return restTemplate
				.getForObject("http://localhost:8080/product/" + id, ProductDto.class);
	}
}
