package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.Product
import com.lincoln4791.ecommerce.model.enums.ApiStatus
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.repository.ProductRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProductService(private val repo: ProductRepository) {

    fun getAll(): ResponseEntity<Any> {
        val items = repo.findAll()
        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatus.Success.name,
                errors = null,
                data = items
            )
        )
    }

    fun add(product: Product): ResponseEntity<Any> {

        val existing = repo.findByProductId(product.productId)

        val savedItem = if (existing != null) {
            val updated = existing.copy(
                stock = existing.stock + product.stock,
                price = product.price
            )
            repo.save(updated)
        } else {
            repo.save(product)
        }

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatus.Success.name,
                errors = null,
                data = savedItem
            )
        )
    }
}
