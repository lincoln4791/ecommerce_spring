package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.service.OrderService
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(private val service: OrderService) {

    @PostMapping("/place")
    fun placeOrder(@RequestParam userId: Long) = service.placeOrder(userId)


    @GetMapping("/orders")
    fun getMyOrders(authentication: Authentication): List<Order> = service.getMyOrders(authentication)

}
