package com.lincoln4791.ecommerce.model.responses.model

import com.lincoln4791.ecommerce.model.responses.brand.BrandResponse

data class ProductModelResponse(
    val id: Long,
    val name: String,
    val description : String,
    val brand : BrandResponse,
)