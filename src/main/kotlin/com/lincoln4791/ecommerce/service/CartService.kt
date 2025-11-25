package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.Cart
import com.lincoln4791.ecommerce.repository.CartRepository
import com.lincoln4791.ecommerce.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepo: CartRepository,
    private val productRepo: ProductRepository
) {
    fun addToCart(userId: Long, productId: Long, qty: Int): Cart {
        val product = productRepo.findById(productId)
            .orElseThrow { RuntimeException("Product not found") }

        return cartRepo.save(
            Cart(
                userId = userId,
                product = product,
                quantity = qty
            )
        )
    }

    fun getUserCart(userId: Long) = cartRepo.findAll().filter { it.userId == userId }
}
