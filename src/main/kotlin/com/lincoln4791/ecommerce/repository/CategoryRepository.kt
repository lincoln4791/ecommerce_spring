package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.Cart
import com.lincoln4791.ecommerce.model.entities.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>{
    fun findByName(name: String): Category?
}
