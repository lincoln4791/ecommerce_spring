package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.repository.CartRepository
import com.lincoln4791.ecommerce.repository.OrderItemRepository
import com.lincoln4791.ecommerce.repository.OrderRepository
import com.lincoln4791.ecommerce.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class OrderItemService(
    private val orderRepo: OrderRepository,
    private val orderItemRepo: OrderItemRepository,
    private val cartRepo: CartRepository,
    private val userRepo: UserRepository
) {


}
