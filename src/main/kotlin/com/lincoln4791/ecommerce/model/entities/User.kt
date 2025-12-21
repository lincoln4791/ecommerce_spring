package com.lincoln4791.ecommerce.model.entities

import com.lincoln4791.ecommerce.model.enums.RoleEnum
import com.lincoln4791.ecommerce.model.responses.LoginResponse
import com.lincoln4791.ecommerce.model.responses.ProfileResponse
import com.lincoln4791.ecommerce.model.responses.SignUpResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name : String,

    @Column(unique = true)
    val email: String,

    @Column(unique = true)
    var phone: String,

    val password: String,

    @Enumerated(EnumType.STRING)
    val role : RoleEnum? = RoleEnum.USER
)

fun User.toLoginResponse(token:String,refreshToken: String) : LoginResponse{
    return LoginResponse(
        name = this.name,
        phone = this.phone,
        email = this.email,
        token = token,
        role = this.role,
        refreshToken = refreshToken
    )
}

fun User.toSignUpResponse(token:String) : SignUpResponse{
    return SignUpResponse(
        name = this.name,
        phone = this.phone,
        email = this.email,
        token = token,
        role = this.role
    )
}

fun User.toProfileResponse() : ProfileResponse{
    return ProfileResponse(
        name = this.name,
        phone = this.phone,
        email = this.email,
        role = this.role,
    )
}
