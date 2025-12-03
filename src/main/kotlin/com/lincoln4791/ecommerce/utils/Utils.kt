package com.lincoln4791.ecommerce.utils

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

object Utils {
    fun createPageable(page: Int, size: Int, sort: String): Pageable {
        val sortParts = sort.split(",")
        val sortBy = sortParts[0]
        val direction = if (sortParts.size > 1 && sortParts[1].equals("desc", true))
            Sort.Direction.DESC else Sort.Direction.ASC

        return PageRequest.of(page, size, Sort.by(direction, sortBy))
    }
}