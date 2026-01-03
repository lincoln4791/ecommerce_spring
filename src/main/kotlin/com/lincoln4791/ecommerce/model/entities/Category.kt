package com.lincoln4791.ecommerce.model.entities

import com.lincoln4791.ecommerce.model.responses.brand.BrandResponse
import com.lincoln4791.ecommerce.model.responses.category.CategoryResponse
import jakarta.persistence.*

@Entity
@Table(name = "category")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val name: String,

    val description: String? = null
)
fun Category.toCategoryResponse() = CategoryResponse(
    id = this.id!!,
    name = this.name,
    description=this.description?:""
)
