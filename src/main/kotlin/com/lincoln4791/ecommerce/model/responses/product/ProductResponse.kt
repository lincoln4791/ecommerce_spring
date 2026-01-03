package com.lincoln4791.ecommerce.model.responses.product

import com.lincoln4791.ecommerce.model.responses.category.CategoryResponse
import com.lincoln4791.ecommerce.model.responses.model.ProductModelResponse

data class ProductResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val stock: Int,
    val category: CategoryResponse,
    val model: ProductModelResponse,
    val productStatus: String,
)