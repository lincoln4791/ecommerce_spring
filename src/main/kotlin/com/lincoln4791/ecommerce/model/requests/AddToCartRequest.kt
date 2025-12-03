package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.constraints.NotBlank

data class AddToCartRequest(
    @field:NotBlank(message = "product_id is required")
    val productId: Long,
    @field:NotBlank(message = "quantity is required")
    val quantity: Int
)