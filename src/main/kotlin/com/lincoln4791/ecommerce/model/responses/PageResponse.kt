package com.lincoln4791.ecommerce.model.responses

data class PageResponse<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val total: Long,
    val totalPages: Int,
    val isLast: Boolean
)