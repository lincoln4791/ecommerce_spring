package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>{
    fun findByUserId(userId: Long): List<Order>
}