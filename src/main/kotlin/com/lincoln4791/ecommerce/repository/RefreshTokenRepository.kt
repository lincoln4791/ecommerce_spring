package com.lincoln4791.ecommerce.repository

import com.lincoln4791.ecommerce.model.entities.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String): RefreshToken?
    @Transactional
    fun deleteByUserId(userId: Long)
}
