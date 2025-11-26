package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.model.entities.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemRepository : JpaRepository<OrderItem, Long>{
    fun findByOrderUserId(userId: Long): List<OrderItem>
}