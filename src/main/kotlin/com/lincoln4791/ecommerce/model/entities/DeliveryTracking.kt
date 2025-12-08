package com.lincoln4791.ecommerce.model.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.lincoln4791.ecommerce.model.enums.OrderStatusEnum
import jakarta.persistence.*

@Entity
@Table(name = "delivery_tracking")
data class DeliveryTracking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    val order: Order,
    val status: String=OrderStatusEnum.Pending.name,
    val timestamp: Long = System.currentTimeMillis()
){

}
