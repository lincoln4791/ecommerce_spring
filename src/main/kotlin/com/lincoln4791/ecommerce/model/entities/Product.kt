package com.lincoln4791.ecommerce.model.entities

import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true, nullable = false, name = "product_id")
    val productId: String,
    val name: String,
    var price: Double,
    var stock: Int
)
