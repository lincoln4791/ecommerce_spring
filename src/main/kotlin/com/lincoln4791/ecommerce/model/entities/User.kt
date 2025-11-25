package com.lincoln4791.ecommerce.model.entities

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name : String,

    @Column(unique = true)
    val email: String,

    @Column(unique = true)
    val phone: String,

    val password: String
)
