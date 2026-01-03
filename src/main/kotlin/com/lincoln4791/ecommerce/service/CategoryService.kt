package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.exceptions.ProductNotFoundException
import com.lincoln4791.ecommerce.exceptions.UniqueConstraintException
import com.lincoln4791.ecommerce.model.entities.Category
import com.lincoln4791.ecommerce.model.entities.toCategoryResponse
import com.lincoln4791.ecommerce.model.entities.toProductResponse
import com.lincoln4791.ecommerce.model.enums.ApiStatusEnum
import com.lincoln4791.ecommerce.model.enums.ProductStatusEnum
import com.lincoln4791.ecommerce.model.requests.AddCategoryRequest
import com.lincoln4791.ecommerce.model.requests.ProductStatusUpdateRequest
import com.lincoln4791.ecommerce.model.responses.brand.BaseResponse
import com.lincoln4791.ecommerce.repository.CategoryRepository
import com.lincoln4791.ecommerce.utils.Utils.createPageable
import com.lincoln4791.ecommerce.utils.toPageResponse
import org.springframework.cache.annotation.CacheEvict
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CategoryService(private val repo: CategoryRepository) {

    fun getAll(page: Int, size: Int, sort: String): BaseResponse<Any> {
        print("get from db")
        val pageable = createPageable(page, size, sort)
        val pageResult =  repo.findAll(pageable)
        return BaseResponse(
            status_code = 200,
            message = ApiStatusEnum.Success.name,
            errors = null,
            data = pageResult.toPageResponse{it.toCategoryResponse()}
        )
    }

    @CacheEvict(value = ["product"], allEntries = true)
    fun add(req: AddCategoryRequest): ResponseEntity<Any> {
        val existingCategory = repo.findByName(req.name!!)
        if (existingCategory==null) {
            val savedItem =  repo.save(Category(name = req.name, description = req.description))
            return ResponseEntity.ok(
                BaseResponse(
                    status_code = 200,
                    message = ApiStatusEnum.Success.name,
                    errors = null,
                    data = savedItem.toCategoryResponse()
                )
            )
        }
        else {
            throw UniqueConstraintException("Category Already Exists")
        }


    }

/*    fun delete(id: Long): ResponseEntity<Any> {

        val products = repo.findById(id).orElseThrow{ProductNotFoundException("Not Found")}

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
    }*/

/*    fun inactive(req: ProductStatusUpdateRequest): ResponseEntity<Any> {

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
    }*/

}
