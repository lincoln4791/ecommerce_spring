package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.exceptions.ProductNotFoundException
import com.lincoln4791.ecommerce.model.entities.Product
import com.lincoln4791.ecommerce.model.entities.toProductResponse
import com.lincoln4791.ecommerce.model.enums.ApiStatusEnum
import com.lincoln4791.ecommerce.model.enums.ProductStatusEnum
import com.lincoln4791.ecommerce.model.requests.AddProductRequest
import com.lincoln4791.ecommerce.model.requests.ProductStatusUpdateRequest
import com.lincoln4791.ecommerce.model.responses.brand.BaseResponse
import com.lincoln4791.ecommerce.repository.ProductRepository
import com.lincoln4791.ecommerce.utils.Utils.createPageable
import com.lincoln4791.ecommerce.utils.toPageResponse
import org.springframework.cache.annotation.CacheEvict
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProductService(private val repo: ProductRepository) {

    //@Cacheable("product")
    fun getAll(page: Int, size: Int, sort: String, categoryId: List<Long>?,brandId : List<Long>?): BaseResponse<Any> {
        print("get from db")
        val pageable = createPageable(page, size, sort)
        val pageResult =
            if (categoryId == null && brandId == null) {
                repo.findAll(pageable)
            } else if (categoryId == null && brandId != null) {
                repo.findAllByModel_Brand_IdIn(brandId, pageable)
            }
            else if (categoryId != null && brandId == null) {
                repo.findAllByCategoryIdIn(categoryId, pageable)
            }else {
                repo.findAllByCategoryIdInAndModel_Brand_IdIn(categoryId!!,brandId!!, pageable)
            }
        return BaseResponse(
            status_code = 200,
            message = ApiStatusEnum.Success.name,
            errors = null,
            data = pageResult.toPageResponse{it.toProductResponse()}
        )
    }

    //@CachePut(cacheNames = ["product"], key = "#product.id")
    @CacheEvict(value = ["product"], allEntries = true)
    fun add(req: AddProductRequest): ResponseEntity<Any> {
        val existing = repo.findById(req.productId!!).orElseThrow{ProductNotFoundException("Product Not Found")}

        val savedItem = if (existing != null) {
            val updated = existing.copy(
                stock = existing.stock + req.stock!!,
                price = req.price!!
            )
            repo.save(updated)
        } else {
            val product = Product(
                name = req.name!!,
                price = req.price!!,
                stock = req.stock!!,
                categoryId = req.categoryId!!,
                modelId = req.modelId!!,
                productStatus = ProductStatusEnum.ACTIVE.name
            )
            repo.save(product)
        }

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatusEnum.Success.name,
                errors = null,
                data = savedItem.toProductResponse()
            )
        )
    }

    fun delete(req: ProductStatusUpdateRequest): ResponseEntity<Any> {

        // Fetch all products
        val products = repo.findAllById(req.productIds)

        if (products.isEmpty()) {
            throw ProductNotFoundException("No products found with given IDs")
        }

        // Soft delete each product
        products.forEach { product ->
            product.productStatus = ProductStatusEnum.DELETED.name
        }

        // Save all at once (faster + cleaner)
        repo.saveAll(products)

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = "Products Deleted Successfully",
                errors = null,
                data = products.map { it.toProductResponse() }
            )
        )
    }

    fun inactive(req: ProductStatusUpdateRequest): ResponseEntity<Any> {

        // Fetch all products
        val products = repo.findAllById(req.productIds)

        if (products.isEmpty()) {
            throw ProductNotFoundException("No products found with given IDs")
        }

        // Soft delete each product
        products.forEach { product ->
            product.productStatus = ProductStatusEnum.INACTIVE.name
        }

        // Save all at once (faster + cleaner)
        repo.saveAll(products)

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = "Products Deactivated successfully",
                errors = null,
                data = products.map { it.toProductResponse() }
            )
        )
    }

    fun active(req: ProductStatusUpdateRequest): ResponseEntity<Any> {

        // Fetch all products
        val products = repo.findAllById(req.productIds)

        if (products.isEmpty()) {
            throw ProductNotFoundException("No products found with given IDs")
        }

        // Soft delete each product
        products.forEach { product ->
            product.productStatus = ProductStatusEnum.ACTIVE.name
        }

        // Save all at once (faster + cleaner)
        repo.saveAll(products)

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = "Products Activated Successfully",
                errors = null,
                data = products.map { it.toProductResponse() }
            )
        )
    }

}
