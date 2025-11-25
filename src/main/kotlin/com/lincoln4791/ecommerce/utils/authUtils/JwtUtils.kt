package com.lincoln4791.ecommerce.utils.authUtils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils {
    private val secret = Keys.secretKeyFor(SignatureAlgorithm.HS512) // 👈 secure 512-bit key
    private val expirationMs = 86400000 // 1 day

    fun generateToken(username: String, email:String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationMs)

        return Jwts.builder()
            .setSubject(email)
            .claim("email", email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secret,SignatureAlgorithm.HS512)
            .compact()
    }

    fun extractEmail(token: String): String =
        Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject

    fun validateToken(token: String): Boolean =
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
}