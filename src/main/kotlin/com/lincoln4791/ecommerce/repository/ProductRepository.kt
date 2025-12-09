package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>{
    fun findByProductId(productId: Long): Product?
    fun findAllByCategoryIdIn(productId: List<Long>,pageable:Pageable): Page<Product>
}
