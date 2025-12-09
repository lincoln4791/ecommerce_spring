package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.Product
import com.lincoln4791.ecommerce.model.enums.ApiStatusEnum
import com.lincoln4791.ecommerce.model.requests.AddProductRequest
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.repository.ProductRepository
import com.lincoln4791.ecommerce.utils.Utils.createPageable
import com.lincoln4791.ecommerce.utils.toPageResponse
import org.springframework.cache.annotation.CacheEvict
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProductService(private val repo: ProductRepository) {

    //@Cacheable("product")
    fun getAll(page: Int, size: Int, sort: String, categoryId: List<Long>?): BaseResponse<Any> {
        print("get from db")
        val pageable = createPageable(page, size, sort)
        val pageResult = if (categoryId == null) {
            repo.findAll(pageable)
        } else {
            repo.findAllByCategoryIdIn(categoryId, pageable)
        }
        return BaseResponse(
            status_code = 200,
            message = ApiStatusEnum.Success.name,
            errors = null,
            data = pageResult.toPageResponse()
        )
    }

    //@CachePut(cacheNames = ["product"], key = "#product.id")
    @CacheEvict(value = ["product"], allEntries = true)
    fun add(req: AddProductRequest): ResponseEntity<Any> {
        val existing = repo.findByProductId(req.productId!!)

        val savedItem = if (existing != null) {
            val updated = existing.copy(
                stock = existing.stock + req.stock!!,
                price = req.price!!
            )
            repo.save(updated)
        } else {
            val product = Product(
                productId = req.productId,
                name = req.name!!,
                price = req.price!!,
                stock = req.stock!!,
                categoryId = req.categoryId!!,
                modelId = req.modelId!!
            )
            repo.save(product)
        }

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatusEnum.Success.name,
                errors = null,
                data = savedItem
            )
        )
    }
}
