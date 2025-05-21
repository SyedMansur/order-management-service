package com.wipro.ordermanagement.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartResponse {
	
	private Integer id;
	private String userId;
	
	private Map<Integer, Integer> productQty = new HashMap<>();
	private double totalPrice;
	private Integer totalQuantity;
	
	private String ptoductName;
	private String productDesc;
	private double productPrice;
	private String prodCat;
	private String imageURL;
	
}
