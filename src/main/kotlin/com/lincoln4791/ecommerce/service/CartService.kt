package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.exceptions.EmptyCartException
import com.lincoln4791.ecommerce.exceptions.ProductNotFoundException
import com.lincoln4791.ecommerce.exceptions.UserNotFoundException
import com.lincoln4791.ecommerce.model.entities.Cart
import com.lincoln4791.ecommerce.model.enums.ApiStatusEnum
import com.lincoln4791.ecommerce.model.enums.CartUpdateEnum
import com.lincoln4791.ecommerce.model.requests.CartUpdateRequest
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
            .orElseThrow { ProductNotFoundException("Product not found") }

        val user = userRepo.findByEmail(authentication.name)
            ?: throw UserNotFoundException("User Not Found")

        // Check if item already exists
        val existingItem = cartRepo.findByUserIdAndProductId(user.id, productId)

        val savedItem = if (existingItem == null) {

            // Create new cart item
            cartRepo.save(
                Cart(
                    userId = user.id,
                    product = product,
                    quantity = qty
                )
            )

        } else {

            // Increase quantity
            existingItem.quantity += qty
            cartRepo.save(existingItem)
        }

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatusEnum.Success.name,
                errors = null,
                data = savedItem
            )
        )
    }


    fun removeFromCart(
        authentication: Authentication,
        cartUpdateRequest: CartUpdateRequest
    ): ResponseEntity<Any> {

        val email = authentication.name
        val user = userRepo.findByEmail(email)
            ?: throw UserNotFoundException("User Not Found")

        val product = productRepo.findById(cartUpdateRequest.id)
            .orElseThrow { ProductNotFoundException("Product not found") }

        val cartItem = cartRepo.findByUserIdAndProductId(user.id, cartUpdateRequest.id)
            ?: throw EmptyCartException("Item not found in cart")

        when(cartUpdateRequest.action) {
            CartUpdateEnum.DELETE.name -> {
                cartRepo.delete(cartItem)
            }

            CartUpdateEnum.DECREASE.name -> {
                if (cartItem.quantity <= cartUpdateRequest.qty) {
                    cartRepo.delete(cartItem)
                } else {
                    cartItem.quantity -= cartUpdateRequest.qty
                    cartRepo.save(cartItem)
                }
            }

            else -> throw IllegalArgumentException("Invalid action: ${cartUpdateRequest.action}")
        }

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatusEnum.Success.name,
                errors = null,
                data = cartItem
            )
        )
    }


    fun getUserCart(authentication: Authentication) : ResponseEntity<Any> {

        val email = authentication.name
        val user = userRepo.findByEmail(email)?:return ResponseEntity.ok(BaseResponse(
            status_code=401,
            message= ApiStatusEnum.Failed.name,
            errors = "User not found",
            data =  null
        ))

        //val items = cartRepo.findAll().filter { it.userId == user.id }
        val items = cartRepo.findByUserId(user.id)

        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message= ApiStatusEnum.Success.name,
            errors = null,
            data =  items
        ))

    }
}
