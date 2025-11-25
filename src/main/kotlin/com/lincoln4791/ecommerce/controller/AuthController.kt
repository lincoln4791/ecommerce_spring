package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.entities.User
import com.lincoln4791.ecommerce.model.enums.ApiStatus
import com.lincoln4791.ecommerce.model.requests.LoginRequest
import com.lincoln4791.ecommerce.model.requests.SignupRequest
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.model.responses.LoginResponse
import com.lincoln4791.ecommerce.repository.UserRepository
import com.lincoln4791.ecommerce.utils.authUtils.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepo: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/signup")
    fun signup(@RequestBody req: SignupRequest): ResponseEntity<Any> {
        if (userRepo.existsByEmail(req.email)) {
            return ResponseEntity.ok(BaseResponse(
                status_code=200,
                message=ApiStatus.Failed.name,
                errors = "User Already Exists",
                data =  null
            ))
        }

        val user = User(
            name = req.name,
            email = req.email,
            phone = req.phone,
            password = passwordEncoder.encode(req.password)
        )
        userRepo.save(user)
        val token = jwtUtils.generateToken(user.name,user.email)
        val response = LoginResponse(
            name = user.name,
            phone = user.phone,
            email = user.email,
            token = token
        )
        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message=ApiStatus.Success.name,
            errors = null,
            data =  response
        ))
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {
        val user = userRepo.findByEmail(req.email)
            ?: return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))

        if (!passwordEncoder.matches(req.password, user.password)) {
            return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        }

        val token = jwtUtils.generateToken(user.name,user.email)
        val response = LoginResponse(
            name = user.name,
            phone = user.phone,
            email = user.email,
            token = token
        )

        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message=ApiStatus.Success.name,
            errors = null,
            data =  response
        ))

    }
}
