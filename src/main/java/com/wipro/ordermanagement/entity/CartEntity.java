package com.wipro.ordermanagement.entity;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity(name = "CART_DETAILS")
@Data
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class CartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "USER_ID", nullable = false)
    private Integer userId;
	
    @ElementCollection
    @CollectionTable(name = "cart_product_qty", joinColumns = @JoinColumn(name = "cart_item_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "PROD_QTY")
    private Map<Integer, Integer> productQty = new HashMap<>();
    
    @Column(name = "TOT_PICE")
    private Double totalPrice;
    
    @Column(name = "TOT_QTY")
    private Integer totalQuantity;
}
