package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.model.entities.OrderItem
import com.lincoln4791.ecommerce.model.enums.ApiStatus
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.repository.CartRepository
import com.lincoln4791.ecommerce.repository.OrderItemRepository
import com.lincoln4791.ecommerce.repository.OrderRepository
import com.lincoln4791.ecommerce.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class OrderItemService(
    private val orderRepo: OrderRepository,
    private val orderItemRepo: OrderItemRepository,
    private val cartRepo: CartRepository,
    private val userRepo: UserRepository
) {


}
