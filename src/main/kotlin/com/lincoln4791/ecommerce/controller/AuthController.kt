package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.requests.LoginRequest
import com.lincoln4791.ecommerce.model.requests.SignupRequest
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody req: SignupRequest): ResponseEntity<Any> = authService.signup(req)

    @PostMapping("/login")
    fun login(@Valid@RequestBody req: LoginRequest): ResponseEntity<Any> = authService.login(req)

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: Map<String, String>): BaseResponse<Any> = authService.refreshToken(request)

}
