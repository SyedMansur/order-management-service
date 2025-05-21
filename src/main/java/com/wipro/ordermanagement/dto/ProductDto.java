package com.wipro.ordermanagement.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDto {

	private int id;

	private String productName;

	private String productDesc;

	private double productPrice;

	private double totProdPrice;

	private String prodCat;

	private int productQuantity;

	private String uom; // litre,kg,pc

	private double prodRating;

	private String imageURL;

	private Date createDate;

	private Date modifyDate;
}
