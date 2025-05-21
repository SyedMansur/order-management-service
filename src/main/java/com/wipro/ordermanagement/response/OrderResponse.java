package com.wipro.ordermanagement.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponse {

	private int orderId;
	private String ptoductName;
	private double totalPrice;	
	private Integer totalQuantity;
	private String status;
	private String productDesc;
	private String imageURL;
	
}
