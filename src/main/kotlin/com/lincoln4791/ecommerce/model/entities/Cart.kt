package com.lincoln4791.ecommerce.model.entities

import com.lincoln4791.ecommerce.model.responses.cart.CartResponse
import jakarta.persistence.*

@Entity
@Table(name = "carts")
data class Cart(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id")
    val userId: Long,

    @ManyToOne
    val product: Product,

    var quantity: Int
)


fun Cart.toCartResponse() : CartResponse{
    return CartResponse(
        id,
        userId,
        product.toProductResponse(),
        quantity
    )
}
