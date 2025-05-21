package com.wipro.ordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.ordermanagement.entity.OrderEntity;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Integer>{

	List<OrderEntity> findByUserId(String userId);
	
}
