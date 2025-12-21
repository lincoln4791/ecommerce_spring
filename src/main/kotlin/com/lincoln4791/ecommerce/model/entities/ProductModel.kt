package com.lincoln4791.ecommerce.model.entities

import com.lincoln4791.ecommerce.model.responses.BrandResponse
import com.lincoln4791.ecommerce.model.responses.ProductModelResponse
import jakarta.persistence.*

@Entity
@Table(name = "models")
data class ProductModel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "brand_id")
    val brandId :Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id" ,insertable = false, updatable = false)
    val brand :Brand,

    val name: String,

    val description : String?=null

)

fun ProductModel.toProductModelResponse() = ProductModelResponse(
    id = this.id,
    name = this.name,
    description=this.description?:"",
    brand = this.brand.toBrandResponse()
)
