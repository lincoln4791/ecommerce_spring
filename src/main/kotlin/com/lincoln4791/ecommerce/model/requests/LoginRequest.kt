package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "email is required")
    val email: String,
    @field:NotBlank(message = "password is required")
    val password: String
)