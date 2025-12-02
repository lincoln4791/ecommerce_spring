package com.lincoln4791.ecommerce.model.responses

import com.lincoln4791.ecommerce.model.enums.Role

data class LoginResponse(
    val name : String,
    val phone : String,
    val email: String,
    val token: String,
    val refreshToken: String?,
    val role :Role?
)