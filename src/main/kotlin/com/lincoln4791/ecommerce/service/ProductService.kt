package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.Product
import com.lincoln4791.ecommerce.model.enums.ApiStatus
import com.lincoln4791.ecommerce.model.requests.AddProductRequest
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.repository.ProductRepository
import com.lincoln4791.ecommerce.utils.Utils.createPageable
import com.lincoln4791.ecommerce.utils.toPageResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProductService(private val repo: ProductRepository) {

    fun getAll(page:Int,size:Int,sort:String): ResponseEntity<Any> {
        val pageable = createPageable(page, size, sort)
        val pageResult = repo.findAll(pageable)
        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatus.Success.name,
                errors = null,
                data = pageResult.toPageResponse()
            )
        )
    }

    fun add(req: AddProductRequest): ResponseEntity<Any> {

/*        val existing = repo.findByProductId(product.productId)

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
        )*/

        val existing = repo.findByProductId(req.productId!!)

        val savedItem = if (existing != null) {
            val updated = existing.copy(
                stock = existing.stock + req.stock!!,
                price = req.price!!
            )
            repo.save(updated)
        } else {
            val product = Product(
                productId = req.productId!!,
                name = req.name!!,
                price = req.price!!,
                stock = req.stock!!
            )
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
