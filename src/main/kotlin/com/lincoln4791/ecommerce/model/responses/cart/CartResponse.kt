package com.lincoln4791.ecommerce.model.responses.cart

import com.lincoln4791.ecommerce.model.responses.product.ProductResponse

data class CartResponse(
    val id: Long = 0,
    val userId: Long,
    val product: ProductResponse,
    var quantity: Int
)