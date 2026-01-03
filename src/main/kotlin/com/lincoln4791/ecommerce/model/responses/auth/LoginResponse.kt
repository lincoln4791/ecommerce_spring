package com.lincoln4791.ecommerce.model.responses.auth

import com.lincoln4791.ecommerce.model.enums.RoleEnum

data class LoginResponse(
    val name : String,
    val phone : String,
    val email: String,
    val token: String,
    val refreshToken: String?,
    val role : RoleEnum?
)