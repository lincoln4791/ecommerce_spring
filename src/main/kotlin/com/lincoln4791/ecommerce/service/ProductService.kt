package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.Product
import com.lincoln4791.ecommerce.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val repo: ProductRepository) {

    fun getAll() = repo.findAll()

    fun add(product: Product) = repo.save(product)
}
