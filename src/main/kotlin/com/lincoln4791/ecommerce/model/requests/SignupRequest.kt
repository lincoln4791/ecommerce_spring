package com.lincoln4791.ecommerce.model.requests

data class SignupRequest(
    val name : String,
    val email : String,
    val phone: String,
    val password: String
)


