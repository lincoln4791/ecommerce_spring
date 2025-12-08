package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class AddToCartRequest(
    @field:NotNull(message = "product_id ID is required")
    @field:Positive(message = "product_id must be positive")
    val productId: Long,
    @field:NotNull(message = "quantity is required")
    @field:Positive(message = "quantity must be positive")
    val quantity: Int
)