package com.lincoln4791.ecommerce.model.requests.cart



data class CartBulkUpdateRequest(
    val items: List<CartBulkUpdateItems>
)


data class CartBulkUpdateItems(
    val productId: Long,
    val quantity: Int
)
