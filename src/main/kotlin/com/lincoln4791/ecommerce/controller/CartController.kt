package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.requests.AddToCartRequest
import com.lincoln4791.ecommerce.service.CartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(private val service: CartService) {

    @PostMapping("/add")
    fun addToCart(@RequestBody req: AddToCartRequest) =
        service.addToCart(req.userId, req.productId, req.quantity)

    @GetMapping("/{userId}")
    fun getCart(@PathVariable userId: Long) = service.getUserCart(userId)
}
