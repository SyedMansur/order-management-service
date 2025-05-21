package com.wipro.ordermanagement.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDetails {

	/**
	 * Product name
	 */
	private String productName;

	/**
	 * Product Description
	 */
	private String productDesc;

	/**
	 * Double(wrapper class -for null check
	 */
	private Double productPrice;

	/**
	 * Product category
	 */
	private String prodCat;

	/**
	 * Integer(wrapper class) -for null check
	 */
	private Integer productQuantity;

	/**
	 * Liter, kg, pc
	 */
	private String uom;

	/**
	 * Product Rating out of 5
	 */
	private double prodRating;

	/**
	 * Image url
	 */
	private String imageURL;
}
