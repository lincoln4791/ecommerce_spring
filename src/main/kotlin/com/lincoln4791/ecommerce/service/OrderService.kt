package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.exceptions.EmptyCartException
import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.repository.CartRepository
import com.lincoln4791.ecommerce.repository.OrderRepository
import com.lincoln4791.ecommerce.repository.UserRepository
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepo: OrderRepository,
    private val cartRepo: CartRepository,
    private val userRepo: UserRepository
) {
    fun placeOrder(userId: Long): Order {
        val cartItems = cartRepo.findAll().filter { it.userId == userId }

        if (cartItems.isEmpty()) {
            throw EmptyCartException("Cart is empty. Cannot place order.")
        }

        val total = cartItems.sumOf { it.product.price * it.quantity }

        cartRepo.deleteAll(cartItems)

        return orderRepo.save(
            Order(
                userId = userId,
                totalAmount = total
            )
        )
    }

    fun getMyOrders(authentication: Authentication): List<Order> {

        val email = authentication.name
        val user = userRepo.findByEmail(email) ?: throw RuntimeException("User not found")

        return orderRepo.findByUserId(user.id);
    }

}
