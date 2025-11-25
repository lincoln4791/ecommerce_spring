package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long>{
    fun findByUserIdAndProductId(userId: Long, productId: Long): Cart?
    fun findByUserId(userId: Long): List<Cart>
}