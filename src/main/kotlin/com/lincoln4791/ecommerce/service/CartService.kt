package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.Cart
import com.lincoln4791.ecommerce.model.enums.ApiStatus
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.repository.CartRepository
import com.lincoln4791.ecommerce.repository.ProductRepository
import com.lincoln4791.ecommerce.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepo: CartRepository,
    private val productRepo: ProductRepository,
    private val userRepo: UserRepository
) {
    fun addToCart(authentication: Authentication, productId: Long, qty: Int): ResponseEntity<Any> {
        val product = productRepo.findById(productId)
            .orElseThrow { RuntimeException("Product not found") }

        val email = authentication.name
        val user = userRepo.findByEmail(email) ?: return ResponseEntity.ok(BaseResponse(
            status_code=401,
            message= ApiStatus.Failed.name,
            errors = "User not found",
            data =  null
        ))

        val savedItem = cartRepo.save(
            Cart(
                userId = user.id,
                product = product,
                quantity = qty,
            )
        )
        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message= ApiStatus.Success.name,
            errors = null,
            data =  savedItem
        ))
    }

    fun getUserCart(authentication: Authentication) : ResponseEntity<Any> {

        val email = authentication.name
        val user = userRepo.findByEmail(email)?:return ResponseEntity.ok(BaseResponse(
            status_code=401,
            message= ApiStatus.Failed.name,
            errors = "User not found",
            data =  null
        ))

        //val items = cartRepo.findAll().filter { it.userId == user.id }
        val items = cartRepo.findByUserId(user.id)

        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message= ApiStatus.Success.name,
            errors = null,
            data =  items
        ))

    }
}
