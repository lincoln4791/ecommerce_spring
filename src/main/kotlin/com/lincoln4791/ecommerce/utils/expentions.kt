package com.lincoln4791.ecommerce.utils

import com.lincoln4791.ecommerce.model.responses.PageResponse
import org.springframework.data.domain.Page

/*fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(
        items = this.content,
        page = this.number,
        size = this.size,
        total = this.totalElements,
        totalPages = this.totalPages,
        last = this.isLast
    )
}*/

fun <T, R> Page<T>.toPageResponse(mapper: (T) -> R): PageResponse<R> {
    return PageResponse(
        items = this.content.map(mapper),
        page = this.number,
        size = this.size,
        total = this.totalElements,
        totalPages = this.totalPages,
        last = this.isLast
    )
}
