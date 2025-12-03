package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.constraints.NotBlank

data class UpdateDeliveryStatusRequest(
    @field:NotBlank(message = "order_id is required")
    val order_id: Long,
    @field:NotBlank(message = "status is required")
    val status: String,
) {
}