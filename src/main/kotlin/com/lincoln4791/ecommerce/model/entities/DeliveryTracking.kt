package com.lincoln4791.ecommerce.model.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.lincoln4791.ecommerce.model.enums.OrderStatus
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "delivery_tracking")
data class DeliveryTracking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    val order: Order,
    val status: String=OrderStatus.Pending.name,
    val timestamp: Long = System.currentTimeMillis()
){

}
