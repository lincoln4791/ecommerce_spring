package com.lincoln4791.ecommerce.model.entities

import com.lincoln4791.ecommerce.model.enums.ProductStatusEnum
import com.lincoln4791.ecommerce.model.responses.product.ProductResponse
import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "category_id")
    val categoryId :Long,
    @Column(name = "model_id")
    val modelId :Long,

    val name: String,
    var price: Double,
    var stock: Int,
    var productStatus: String = ProductStatusEnum.ACTIVE.name,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",insertable = false, updatable = false)
    val category :Category?=null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="model_id",insertable = false, updatable = false)
    val model :ProductModel?=null

)

fun Product.toProductResponse() = ProductResponse(
    id = this.id,
    name = this.name,
    price = this.price,
    stock = this.stock,
    productStatus = this.productStatus,
    category = this.category!!.toCategoryResponse(),
    model = this.model!!.toProductModelResponse()
)
