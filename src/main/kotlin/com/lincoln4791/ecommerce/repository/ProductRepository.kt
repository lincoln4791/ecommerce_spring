package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>{
    fun findAllByCategoryIdIn(categoryId: List<Long>,pageable:Pageable): Page<Product>
    fun findAllByModel_Brand_IdIn(brandId: List<Long>,pageable:Pageable): Page<Product>
    fun findAllByCategoryIdInAndModel_Brand_IdIn(categoryId:List<Long>,brandId: List<Long>,pageable:Pageable): Page<Product>
}
