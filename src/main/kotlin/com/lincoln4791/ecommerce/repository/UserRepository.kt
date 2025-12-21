package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByPhone(phone: String): User?
    fun existsByEmail(email: String): Boolean
    fun existsByPhone(phone: String): Boolean

    fun existsByPhoneAndIdNot(phone: String,id:Long): Boolean
}
