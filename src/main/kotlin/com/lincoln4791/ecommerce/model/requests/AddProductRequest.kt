package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AddProductRequest(
    @field:NotNull(message = "Product ID is required")
    val productId: Long? = null,

    @field:NotNull(message = "Category ID is required")
    val categoryId: Long? = null,

    @field:NotNull(message = "Model ID is required")
    val modelId: Long? = null,

    @field:NotBlank(message = "Product name is required")
    val name: String? = null,

    @field:NotNull(message = "Price is required")
    @field:Min(1, message = "Price must be at least 1")
    val price: Double? = null,

    @field:NotNull(message = "Stock is required")
    @field:Min(0, message = "Stock must be 0 or more")
    val stock: Int? = null
)