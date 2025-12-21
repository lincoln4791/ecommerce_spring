package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.User
import com.lincoln4791.ecommerce.model.entities.toLoginResponse
import com.lincoln4791.ecommerce.model.entities.toSignUpResponse
import com.lincoln4791.ecommerce.model.enums.ApiStatusEnum
import com.lincoln4791.ecommerce.model.enums.RoleEnum
import com.lincoln4791.ecommerce.model.requests.LoginRequest
import com.lincoln4791.ecommerce.model.requests.SignupRequest
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.model.responses.LoginResponse
import com.lincoln4791.ecommerce.repository.UserRepository
import com.lincoln4791.ecommerce.utils.authUtils.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val refreshTokenService: RefreshTokenService,
    private val userRepo: UserRepository
) {
    fun signup(req: SignupRequest): ResponseEntity<Any> {
        if (userRepo.existsByEmail(req.email)) {
            return ResponseEntity.ok(BaseResponse(
                status_code=200,
                message=ApiStatusEnum.Failed.name,
                errors = "User Already Exists",
                data =  null
            ))
        }

        val user = User(
            name = req.name!!,
            email = req.email,
            phone = req.phone,
            password = passwordEncoder.encode(req.password),
            role = RoleEnum.USER
        )
        userRepo.save(user)
        val token = jwtUtils.generateToken(user)
        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message=ApiStatusEnum.Success.name,
            errors = null,
            data =  user.toSignUpResponse(token)
        ))
    }


    fun login(req: LoginRequest): ResponseEntity<Any> {
        print("login called")
        val user = userRepo.findByEmail(req.email)
            ?:return ResponseEntity.ok(BaseResponse(
                status_code=401,
                message=ApiStatusEnum.Success.name,
                errors = "Invalid Credential!",
                data =  null
            ))

        if (!passwordEncoder.matches(req.password, user.password)) {
            return ResponseEntity.ok(BaseResponse(
                status_code=401,
                message=ApiStatusEnum.Failed.name,
                errors = "Invalid Credential!",
                data =  null
            ))
        }

        val token = jwtUtils.generateToken(user)
        refreshTokenService.deleteByUserId(user.id)
        val refreshToken = refreshTokenService.createRefreshToken(userId = user.id)


        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message=ApiStatusEnum.Success.name,
            errors = null,
            data =  user.toLoginResponse(token,refreshToken.token)
        ))

    }

    fun refreshToken(request: Map<String, String>): BaseResponse<Any> {
        val refreshToken = refreshTokenService.getByToken(request[""]!!)
        refreshTokenService.verifyExpiration(refreshToken)
        val newAccessToken = jwtUtils.generateToken(refreshToken.user)
        return BaseResponse(
            status_code = 200,
            message = ApiStatusEnum.Success.name,
            errors = null,
            data = mapOf(
                "access_token" to newAccessToken
            )
        )
    }

}
