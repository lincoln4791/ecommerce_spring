package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.requests.AddProductRequest
import com.lincoln4791.ecommerce.model.requests.ProductStatusUpdateRequest
import com.lincoln4791.ecommerce.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val service: ProductService) {

    @GetMapping
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "1000") size: Int,
        @RequestParam(defaultValue = "id,asc") sort: String,
        @RequestParam (required = false) categoryId: List<Long>?,
        @RequestParam (required = false) brandId: List<Long>?
    ) = ResponseEntity.ok(service.getAll(page,size,sort,categoryId,brandId))

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun add(@Valid @RequestBody req: AddProductRequest) = service.add(req)

    @PostMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    fun active(@Valid @RequestBody req: ProductStatusUpdateRequest) = service.active(req)
    @PostMapping("/inactive")
    @PreAuthorize("hasRole('ADMIN')")
    fun inactive(@Valid @RequestBody req: ProductStatusUpdateRequest) = service.inactive(req)

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@Valid @RequestBody req: ProductStatusUpdateRequest) = service.delete(req)
}
