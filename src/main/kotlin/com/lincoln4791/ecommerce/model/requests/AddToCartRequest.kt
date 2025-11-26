package com.lincoln4791.ecommerce.model.requests

data class AddToCartRequest(
    val productId: Long,
    val quantity: Int
)