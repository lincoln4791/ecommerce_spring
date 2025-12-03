package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SignupRequest(
    @field:NotBlank(message = "name is required")
    val name : String,
    @field:NotBlank(message = "email is required")
    val email : String,
    @field:NotBlank(message = "phone is required")
    val phone: String,
    @field:NotBlank(message = "password is required")
    val password: String
)


