package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.constraints.NotEmpty

data class ProductStatusUpdateRequest(
    @field: NotEmpty(message = "Product IDs cannot be empty")
    val productIds: List<Long> = emptyList()
)