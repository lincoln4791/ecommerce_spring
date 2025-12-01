package com.lincoln4791.ecommerce.model.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.lincoln4791.ecommerce.model.enums.OrderStatus
import com.lincoln4791.ecommerce.model.enums.PaymentMethod
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

    var deliveryStatus: String=OrderStatus.Pending.name,
    val paymentMethod: String=PaymentMethod.COD.name,
)
