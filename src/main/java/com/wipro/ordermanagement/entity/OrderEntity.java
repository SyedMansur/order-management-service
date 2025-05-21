package com.wipro.ordermanagement.entity;

import java.time.LocalDateTime;
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
import lombok.Data;

@Entity
@Data
@Table(name = "ORDER_DETAILS")
public class OrderEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name = "USER_ID")
    private Integer userId;
    
	@Column(name = "TOT_PRICE")
    private Double totalPrice;
    
	@Column(name = "TOT_QTY")
    private Integer totalQty;
    
	@Column(name = "ORDER_STATUS")
    private String status; // Example: "PLACED", "CANCELLED"
    
	@Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;
    
    @ElementCollection
    @CollectionTable(name = "order_product_qty", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Integer, Integer> prodDetails;
}
