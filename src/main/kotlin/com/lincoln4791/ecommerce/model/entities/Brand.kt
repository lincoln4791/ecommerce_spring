package com.lincoln4791.ecommerce.model.entities

import com.lincoln4791.ecommerce.model.responses.BrandResponse
import com.lincoln4791.ecommerce.model.responses.ProductResponse
import jakarta.persistence.*

@Entity
@Table(name = "brands")
data class Brand(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    val description : String?=null

)

fun Brand.toBrandResponse() = BrandResponse(
    id = this.id,
    name = this.name,
    description=this.description?:""
)
