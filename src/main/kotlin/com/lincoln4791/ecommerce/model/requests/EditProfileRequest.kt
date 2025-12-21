package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.constraints.NotBlank

data class EditProfileRequest(
    val name: String?=null,
    val phone: String?=null
)