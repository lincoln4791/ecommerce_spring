package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.entities.Product
import com.lincoln4791.ecommerce.model.requests.AddProductRequest
import com.lincoln4791.ecommerce.service.ProductService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val service: ProductService) {

    @GetMapping
    fun getAll() = service.getAll()

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun add(@Valid @RequestBody req: AddProductRequest) = service.add(req)
}
