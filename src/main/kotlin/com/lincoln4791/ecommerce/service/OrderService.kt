package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.exceptions.OrderNotFoundException
import com.lincoln4791.ecommerce.exceptions.OrderStatusNotFoundException
import com.lincoln4791.ecommerce.exceptions.ProductNotFoundException
import com.lincoln4791.ecommerce.exceptions.UniqueConstraintException
import com.lincoln4791.ecommerce.model.entities.DeliveryTracking
import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.model.entities.OrderItem
import com.lincoln4791.ecommerce.model.enums.ApiStatusEnum
import com.lincoln4791.ecommerce.model.enums.OrderStatusEnum
import com.lincoln4791.ecommerce.model.enums.PaymentMethodEnum
import com.lincoln4791.ecommerce.model.requests.UpdateDeliveryStatusRequest
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.repository.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepo: OrderRepository,
    private val cartRepo: CartRepository,
    private val userRepo: UserRepository,
    private val productRepo: ProductRepository,
) {
    fun placeOrder(authentication: Authentication): ResponseEntity<Any> {

        val email = authentication.name
        val user = userRepo.findByEmail(email)
            ?: return ResponseEntity.status(401).body(
                BaseResponse(401, ApiStatusEnum.Failed.name, "User not found", null)
            )


        val cartItems = cartRepo.findByUserId(user.id)
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(400).body(
                BaseResponse(400, ApiStatusEnum.Failed.name, "Cart is empty", null)
            )
        }

        val total = cartItems.sumOf { it.product.price * it.quantity }

        // 1️⃣ Create Order object
        val order = Order(
            userId = user.id,
            totalAmount = total,
            deliveryStatus = OrderStatusEnum.Pending.name,
            paymentMethod = PaymentMethodEnum.COD.name
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

            val product = productRepo.findById(item.product.id).orElseThrow { ProductNotFoundException("Product not found") }
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
            BaseResponse(200, ApiStatusEnum.Success.name, null, savedOrder)
        )
    }

    fun getMyOrders(authentication: Authentication): ResponseEntity<Any> {

        val email = authentication.name
        val user = userRepo.findByEmail(email) ?: throw RuntimeException("Unauthorized")

        val items =  orderRepo.findByUserId(user.id);
        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatusEnum.Success.name,
                errors = null,
                data = items
            )
        )
    }

    fun updateDeliveryStatus(
        authentication: Authentication,
        updateDeliveryStatusRequest: UpdateDeliveryStatusRequest
    ) : ResponseEntity<Any> {
        val order = orderRepo.findById(updateDeliveryStatusRequest.order_id)
            .orElseThrow { OrderNotFoundException("No Order Found") }

        val isExists = OrderStatusEnum.entries.any{it.name==updateDeliveryStatusRequest.status}

        if(isExists){
            if(updateDeliveryStatusRequest.status==OrderStatusEnum.Canceled.name || updateDeliveryStatusRequest.status== OrderStatusEnum.Failed.name){
                if(order.deliveryStatus==OrderStatusEnum.Canceled.name || order.deliveryStatus==OrderStatusEnum.Failed.name){
                    return ResponseEntity.ok(
                        BaseResponse(
                            status_code = 409,
                            message = ApiStatusEnum.Failed.name,
                            errors = "Order Already Canceled/Failed",
                            data = null
                        )
                    )
                }
                else{
                    order.deliveryStatus = updateDeliveryStatusRequest.status
                    val trackingEvent = DeliveryTracking(
                        order = order,
                        status = updateDeliveryStatusRequest.status
                    )
                    order.deliveryTrackingItems.add(trackingEvent)
                    orderRepo.save(order)
                    order.items.forEach {
                        val prod = productRepo.findById(it.product.id).orElseThrow { RuntimeException("Product not found! productId=${it.product.id}") }

                        prod.stock = prod.stock + it.quantity   // restore stock
                        productRepo.save(prod)
                    }
                    return ResponseEntity.ok(
                        BaseResponse(
                            status_code = 200,
                            message = ApiStatusEnum.Success.name,
                            errors = null,
                            data = order
                        )
                    )
                }

            }
            else {
                order.deliveryStatus = updateDeliveryStatusRequest.status
                val trackingEvent = DeliveryTracking(
                    order = order,
                    status = updateDeliveryStatusRequest.status
                )
                order.deliveryTrackingItems.add(trackingEvent)
                try {
                    orderRepo.save(order)
                    return ResponseEntity.ok(
                        BaseResponse(
                            status_code = 200,
                            message = ApiStatusEnum.Success.name,
                            errors = null,
                            data = order
                        )
                    )
                } catch (ex: DataIntegrityViolationException) {
                    throw UniqueConstraintException("Unique Constrains Failed")
                }

            }
        }
        else{
            throw OrderStatusNotFoundException("Order Status ${updateDeliveryStatusRequest.status} is Invalid")
        }




    }


}
