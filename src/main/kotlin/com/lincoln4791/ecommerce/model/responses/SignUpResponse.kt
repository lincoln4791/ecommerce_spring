package com.lincoln4791.ecommerce.model.responses

import com.lincoln4791.ecommerce.model.enums.RoleEnum

data class SignUpResponse(
    val name : String,
    val phone : String,
    val email: String,
    val token: String,
    val role :RoleEnum?
)