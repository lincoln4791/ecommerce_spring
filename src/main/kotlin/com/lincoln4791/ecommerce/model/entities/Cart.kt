package com.lincoln4791.ecommerce.model.entities

import jakarta.persistence.*

@Entity
@Table(name = "carts")
data class Cart(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val userId: Long,

    @ManyToOne
    val product: Product,

    val quantity: Int
)
