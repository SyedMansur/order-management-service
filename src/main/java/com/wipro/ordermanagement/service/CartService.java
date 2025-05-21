package com.wipro.ordermanagement.service;

import java.util.List;

import com.wipro.ordermanagement.dto.CartDto;
import com.wipro.ordermanagement.entity.CartEntity;
import com.wipro.ordermanagement.response.CartResponse;

public interface CartService {

	CartEntity addProductToCart(CartDto cartDto);

	void deleteProductFromCart(int cartId, int productId);

	CartEntity updateProductQty(CartDto cartDto);

	List<CartResponse> getCartByUserId(int userId);
}
