package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.model.entities.OrderItem
import com.lincoln4791.ecommerce.model.enums.ApiStatus
import com.lincoln4791.ecommerce.model.enums.OrderStatus
import com.lincoln4791.ecommerce.model.enums.PaymentMethod
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.repository.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepo: OrderRepository,
    private val cartRepo: CartRepository,
    private val userRepo: UserRepository,
    private val productRepo: ProductRepository
) {
    fun placeOrder(authentication: Authentication): ResponseEntity<Any> {

        val email = authentication.name
        val user = userRepo.findByEmail(email)
            ?: return ResponseEntity.status(401).body(
                BaseResponse(401, ApiStatus.Failed.name, "User not found", null)
            )


        val cartItems = cartRepo.findByUserId(user.id)
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(400).body(
                BaseResponse(400, ApiStatus.Failed.name, "Cart is empty", null)
            )
        }

        val total = cartItems.sumOf { it.product.price * it.quantity }

        // 1️⃣ Create Order object
        val order = Order(
            userId = user.id,
            totalAmount = total,
            deliveryStatus = OrderStatus.Pending.name,
            paymentMethod = PaymentMethod.COD.name
        )

        // 2️⃣ Add items to order.items list
        cartItems.forEach { item ->
            val orderItem = OrderItem(
                order = order,
                product = item.product,
                quantity = item.quantity,
                price = item.product.price
            )

            order.items.add(orderItem)

            val product = productRepo.findByProductId(item.product.productId)
            if (product != null) {
                product.stock = product.stock - orderItem.quantity
                productRepo.save(product)
            }
        }

        // 3️⃣ Save order (this saves items automatically due to cascade)
        val savedOrder = orderRepo.save(order)

        // 4️⃣ Clear cart
        cartRepo.deleteAll(cartItems)

        // 5️⃣ Return the saved order with items populated
        return ResponseEntity.ok(
            BaseResponse(200, ApiStatus.Success.name, null, savedOrder)
        )
    }

    fun getMyOrders(authentication: Authentication): ResponseEntity<Any> {

        val email = authentication.name
        val user = userRepo.findByEmail(email) ?: throw RuntimeException("Unauthorized")

        val items =  orderRepo.findByUserId(user.id);
        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatus.Success.name,
                errors = null,
                data = items
            )
        )
    }

}
