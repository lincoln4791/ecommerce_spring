package com.lincoln4791.ecommerce.model.requests

data class LoginRequest(
    val email: String,
    val password: String
)