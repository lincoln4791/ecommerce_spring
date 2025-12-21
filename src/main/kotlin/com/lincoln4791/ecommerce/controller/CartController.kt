package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.requests.AddToCartRequest
import com.lincoln4791.ecommerce.model.requests.CartUpdateRequest
import com.lincoln4791.ecommerce.service.CartService
import jakarta.validation.Valid
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(private val service: CartService) {

    @PostMapping("/add")
    fun addToCart(authentication: Authentication, @Valid @RequestBody req: AddToCartRequest) =
        service.addToCart(authentication,req.productId!!, req.quantity)

    @DeleteMapping("/removeItemFromCart")
    fun removeItemFromCart(
        authentication: Authentication,
        @RequestBody cardUpdateRequest: CartUpdateRequest
    ) = service.removeFromCart(authentication, cardUpdateRequest)

    @GetMapping("/items")
    fun getCart(authentication: Authentication) = service.getUserCart(authentication)
}
