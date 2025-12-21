package com.lincoln4791.ecommerce.model.requests

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AddCategoryRequest(
    @field:NotBlank(message = "Product name is required")
    val name: String? = null,

    @field:NotBlank(message = "Product name is required")
    val description: String? = null,

)