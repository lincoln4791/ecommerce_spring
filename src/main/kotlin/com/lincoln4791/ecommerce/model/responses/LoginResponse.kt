package com.lincoln4791.ecommerce.model.responses

data class LoginResponse(
    val name : String,
    val phone : String,
    val email: String,
    val token: String
)