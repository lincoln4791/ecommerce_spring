package com.lincoln4791.ecommerce.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EmptyCartException::class)
    fun handleEmptyCart(ex: EmptyCartException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to ex.message!!)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to ex.message!!)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(ex: ProductNotFoundException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to ex.message!!)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UniqueConstraintException::class)
    fun uniqueConstraintException(ex: UniqueConstraintException): ResponseEntity<Map<String, String>> {
        val body = mapOf("error" to ex.message!!)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Any> {

        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        return ResponseEntity.badRequest().body(
            mapOf(
                "error" to "Validation Failed",
                "details" to errors
            )
        )
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(ex: BindException): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        return ResponseEntity.badRequest().body(
            mapOf(
                "status" to 400,
                "error" to "Binding Error",
                "messages" to errors
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseError(ex: HttpMessageNotReadableException): ResponseEntity<Any> {

        val message = ex.mostSpecificCause.message ?: "Invalid request body"

        // Try to detect which field is missing or null
        val field = message
            .substringAfter("property ")
            .substringBefore(" ", "")
            .replace("\"", "")

        val errorMessage = if (field.isNotEmpty()) {
            mapOf(field to "$field is required")
        } else {
            mapOf("error" to "Invalid or missing fields")
        }

        return ResponseEntity.badRequest().body(
            mapOf(
                "status" to 400,
                "error" to "Bad Request",
                "messages" to errorMessage
            )
        )
    }


}