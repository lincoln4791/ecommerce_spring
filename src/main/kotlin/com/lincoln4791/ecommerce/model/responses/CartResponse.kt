package com.lincoln4791.ecommerce.model.responses

import com.lincoln4791.ecommerce.model.entities.Product

data class CartResponse(
    val id: Long = 0,
    val userId: Long,
    val product: ProductResponse,
    var quantity: Int
)