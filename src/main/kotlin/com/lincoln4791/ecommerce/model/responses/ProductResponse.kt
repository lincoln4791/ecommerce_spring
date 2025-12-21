package com.lincoln4791.ecommerce.model.responses
data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val stock: Int,
    val category: CategoryResponse,
    val model: ProductModelResponse,
    val productStatus: String,
)