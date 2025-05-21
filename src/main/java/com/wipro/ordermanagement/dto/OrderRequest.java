package com.wipro.ordermanagement.dto;

import java.util.Map;

import lombok.Data;

@Data
public class OrderRequest {

	private String userId;
	private Map<Integer, Integer> productQty;
}
