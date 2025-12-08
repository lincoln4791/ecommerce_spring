package com.lincoln4791.ecommerce.utils.authUtils

import com.lincoln4791.ecommerce.model.entities.User
import com.lincoln4791.ecommerce.model.enums.RoleEnum
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expirationMs: Long
) {

    private val key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey))

    fun generateToken(user: User): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationMs)

        return Jwts.builder()
            .setSubject(user.email)
            .claim("role", user.role?.name?: RoleEnum.USER)   // <-- HERE
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun extractEmail(token: String): String =
        Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .body.subject

    fun validateToken(token: String): Boolean =
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
}