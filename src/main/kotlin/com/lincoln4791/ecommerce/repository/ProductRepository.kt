package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>{
    fun findByProductId(productId: Long): Product?
}
