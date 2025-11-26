package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orderedItem")
class OrderItemController(private val service: OrderService) {

    @GetMapping("/items")
    fun getMyOrders(authentication: Authentication): ResponseEntity<Any> = service.getMyOrders(authentication)

}
