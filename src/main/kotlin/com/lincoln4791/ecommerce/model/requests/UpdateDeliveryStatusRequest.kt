package com.lincoln4791.ecommerce.model.requests

data class UpdateDeliveryStatusRequest(
    val order_id: Long,
    val status: String,
) {
}