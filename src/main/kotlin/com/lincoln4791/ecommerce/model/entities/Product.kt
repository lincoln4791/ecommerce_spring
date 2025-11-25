package com.lincoln4791.ecommerce.model.entities

import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val price: Double,
    val stock: Int
)
