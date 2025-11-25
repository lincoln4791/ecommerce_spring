package com.lincoln4791.ecommerce.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EmptyCartException::class)
    fun handleEmptyCart(ex: EmptyCartException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to ex.message!!)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}