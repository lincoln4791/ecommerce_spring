package com.lincoln4791.ecommerce.model.responses

import com.lincoln4791.ecommerce.model.entities.Brand
import com.lincoln4791.ecommerce.model.entities.Category
import com.lincoln4791.ecommerce.model.entities.ProductModel

data class CategoryResponse(
    val id: Long,
    val name: String,
    val description : String
)