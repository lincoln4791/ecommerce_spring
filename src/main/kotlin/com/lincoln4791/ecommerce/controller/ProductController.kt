package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.entities.Product
import com.lincoln4791.ecommerce.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val service: ProductService) {

    @GetMapping
    fun getAll() = service.getAll()

    @PostMapping
    fun add(@RequestBody product: Product) = service.add(product)
}
