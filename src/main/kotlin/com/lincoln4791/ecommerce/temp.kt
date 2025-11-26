package com.lincoln4791.ecommerce

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
fun main() {
    val key = Keys.secretKeyFor(SignatureAlgorithm.HS512)
    println(java.util.Base64.getEncoder().encodeToString(key.encoded))
}