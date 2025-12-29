package com.lincoln4791.ecommerce.model.requests.cart

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class AddToCartRequest(
    @field:NotNull(message = "product id ID is required")
    val productId: Long?,

    @field:NotNull(message = "quantity is required")
    @field:Positive(message = "quantity must be positive")
    val quantity: Int
)