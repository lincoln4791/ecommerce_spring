package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.model.requests.UpdateDeliveryStatusRequest
import com.lincoln4791.ecommerce.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(private val service: OrderService) {

    @PostMapping("/placeFromCart")
    fun placeOrder(authentication: Authentication) = service.placeOrder(authentication)


    @GetMapping("/orders")
    fun getMyOrders(authentication: Authentication): ResponseEntity<Any> = service.getMyOrders(authentication)

    @PostMapping("/update_delivery_status")
    fun updateDeliveryStatus(authentication: Authentication,@RequestBody updateDeliveryStatusRequest: UpdateDeliveryStatusRequest) = service.updateDeliveryStatus(authentication,updateDeliveryStatusRequest)

}
