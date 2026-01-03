package com.lincoln4791.ecommerce.model.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.lincoln4791.ecommerce.model.enums.OrderStatusEnum
import com.lincoln4791.ecommerce.model.enums.PaymentMethodEnum
import com.lincoln4791.ecommerce.model.responses.order.OrderResponse
import jakarta.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val userId: Long,
    val totalAmount: Double,
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val items: MutableList<OrderItem> = mutableListOf(),

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val deliveryTrackingItems: MutableList<DeliveryTracking> = mutableListOf(),

    var deliveryStatus: String=OrderStatusEnum.Pending.name,
    val paymentMethod: String=PaymentMethodEnum.COD.name,
)

fun Order.toOrderResponse() : OrderResponse{
    return OrderResponse(
        id,userId,totalAmount,items.map { it.toOrderItemResponse() }.toMutableList(),deliveryStatus,paymentMethod
    )
}
