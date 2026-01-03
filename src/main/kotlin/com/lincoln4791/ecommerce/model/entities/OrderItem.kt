package com.lincoln4791.ecommerce.model.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.lincoln4791.ecommerce.model.responses.order.OrderItemResponse
import jakarta.persistence.*

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    val order: Order,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    val quantity: Int,

    val price: Double
)


fun OrderItem.toOrderItemResponse() : OrderItemResponse{
    return OrderItemResponse(
        id = id,
        product = product.toProductResponse(),
        quantity = quantity,
        price = price
    )
}
