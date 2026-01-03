package com.lincoln4791.ecommerce.model.responses.order

import com.fasterxml.jackson.annotation.JsonBackReference
import com.lincoln4791.ecommerce.model.entities.Order
import com.lincoln4791.ecommerce.model.entities.Product
import com.lincoln4791.ecommerce.model.responses.order.OrderResponse
import com.lincoln4791.ecommerce.model.responses.product.ProductResponse
import jakarta.persistence.*

data class OrderItemResponse(
    val id: Long = 0,
    val product: ProductResponse,
    val quantity: Int,
    val price: Double
)
