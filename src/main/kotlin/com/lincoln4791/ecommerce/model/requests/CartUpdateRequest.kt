package com.lincoln4791.ecommerce.model.requests

import com.lincoln4791.ecommerce.model.enums.CartUpdateEnum
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CartUpdateRequest(
    @field:NotNull(message = "Product ID is required")
    val id: Long,

    @field:NotBlank(message = "action is required")
    val action: String = CartUpdateEnum.ADD.name,

    @field:NotNull(message = "qty is required")
    @field:Min(0, message = "qty must be 0 or more")
    val qty: Int = 1
)