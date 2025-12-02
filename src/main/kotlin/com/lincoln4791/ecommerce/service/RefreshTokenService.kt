package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.RefreshToken
import com.lincoln4791.ecommerce.repository.RefreshTokenRepository
import com.lincoln4791.ecommerce.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class RefreshTokenService(
    private val refreshTokenRepo: RefreshTokenRepository,
    private val userRepo: UserRepository
) {

    fun createRefreshToken(userId: Long): RefreshToken {
        val user = userRepo.findById(userId).orElseThrow()

        val token = UUID.randomUUID().toString()
        val expiry = Instant.now().plus(30, ChronoUnit.DAYS)

        val refreshToken = RefreshToken(
            user = user,
            token = token,
            expiryDate = expiry
        )

        return refreshTokenRepo.save(refreshToken)
    }

    fun verifyExpiration(token: RefreshToken) {
        if (token.expiryDate.isBefore(Instant.now())) {
            refreshTokenRepo.delete(token)
            throw RuntimeException("Refresh token expired")
        }
    }

    fun getByToken(token: String): RefreshToken =
        refreshTokenRepo.findByToken(token)
            ?: throw RuntimeException("Invalid refresh token")


    fun deleteByUserId(userId: Long){
        refreshTokenRepo.deleteByUserId(userId)
    }
}
