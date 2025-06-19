package com.grocery.client.dao;

import com.grocery.client.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByUserIdAndProductId(int userId, int productId);

    List<Cart> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.userId = :userId AND c.product.id = :productId")
    void updateQuantity(@Param("userId") int userId,
                        @Param("productId") int productId,
                        @Param("quantity") int quantity);


}
