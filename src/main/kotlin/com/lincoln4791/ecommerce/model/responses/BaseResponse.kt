package com.lincoln4791.ecommerce.model.responses

data class BaseResponse<T>(
    val status_code: Int,
    val message: String,
    val errors: String? = null,
    val data: T? = null
)