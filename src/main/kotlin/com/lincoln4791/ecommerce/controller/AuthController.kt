package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.entities.User
import com.lincoln4791.ecommerce.model.enums.ApiStatus
import com.lincoln4791.ecommerce.model.enums.Role
import com.lincoln4791.ecommerce.model.requests.LoginRequest
import com.lincoln4791.ecommerce.model.requests.SignupRequest
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.model.responses.LoginResponse
import com.lincoln4791.ecommerce.repository.UserRepository
import com.lincoln4791.ecommerce.service.RefreshTokenService
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
    private val jwtUtils: JwtUtils,
    private val refreshTokenService: RefreshTokenService
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
            password = passwordEncoder.encode(req.password),
            role = Role.USER
        )
        userRepo.save(user)
        val token = jwtUtils.generateToken(user)
        val response = LoginResponse(
            name = user.name,
            phone = user.phone,
            email = user.email,
            token = token,
            role = user.role,
            refreshToken = null
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
            ?:return ResponseEntity.ok(BaseResponse(
                status_code=401,
                message=ApiStatus.Success.name,
                errors = "Invalid Credential!",
                data =  null
            ))

        if (!passwordEncoder.matches(req.password, user.password)) {
            return ResponseEntity.ok(BaseResponse(
                status_code=401,
                message=ApiStatus.Success.name,
                errors = "Invalid Credential!",
                data =  null
            ))
        }

        val token = jwtUtils.generateToken(user)
        refreshTokenService.deleteByUserId(user.id)
        val refreshToken = refreshTokenService.createRefreshToken(userId = user.id)
        val response = LoginResponse(
            name = user.name,
            phone = user.phone,
            email = user.email,
            token = token,
            role = user.role,
            refreshToken = refreshToken.token
        )

        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message=ApiStatus.Success.name,
            errors = null,
            data =  response
        ))

    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: Map<String, String>): BaseResponse<Any> {
        val refreshToken = refreshTokenService.getByToken(request["refresh_token"]!!)
        refreshTokenService.verifyExpiration(refreshToken)

        val newAccessToken = jwtUtils.generateToken(refreshToken.user)

        // Optional: generate new refresh token here (rotation)

        return BaseResponse(
            status_code = 200,
            message = ApiStatus.Success.name,
            errors = null,
            data = mapOf(
                "access_token" to newAccessToken
            )
        )
    }

}
