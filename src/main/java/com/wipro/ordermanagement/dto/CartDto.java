package com.wipro.ordermanagement.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class CartDto {
	
    private String userId;
    private Map<Integer, Integer> productQty = new HashMap<>();
}
