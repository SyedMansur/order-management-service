package com.wipro.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.ordermanagement.dto.CartDto;
import com.wipro.ordermanagement.entity.CartEntity;
import com.wipro.ordermanagement.response.CartResponse;
import com.wipro.ordermanagement.service.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;

	@PostMapping
	public ResponseEntity<String> addProductToCart(@RequestBody CartDto cartDto) {

		cartService.addProductToCart(cartDto);
		return ResponseEntity.ok("Product added to cart");
	}

	@DeleteMapping("/{cartId}/{productId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable int cartId, @PathVariable int productId) {

		cartService.deleteProductFromCart(cartId, productId);
		return ResponseEntity.ok("Product deleted succesfully.");

	}

	@PutMapping
	public ResponseEntity<CartEntity> updateProductQty(@RequestBody CartDto cartDto) {

		CartEntity entity = cartService.updateProductQty(cartDto);

		return ResponseEntity.ok(entity);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<CartResponse>> getCartByUserId(@PathVariable String userId) {

		List<CartResponse> cartResponseList = cartService.getCartByUserId(userId);

		return ResponseEntity.ok(cartResponseList);
	}
}
