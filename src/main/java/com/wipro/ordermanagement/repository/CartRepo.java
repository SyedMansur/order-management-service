package com.wipro.ordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wipro.ordermanagement.entity.CartEntity;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Integer>{

	List<CartEntity> findByUserId(Integer userId);
	
	@Query(value = "select * from cart_details cd inner join cart_product_qty cp on cp.cart_item_id = cd.id "
			+ "where cd.user_id =:userId and cp.product_id =:productId", nativeQuery = true)
    CartEntity findEntity(@Param("userId") Integer userId, @Param("productId") Integer productId);
}
