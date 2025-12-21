package com.lincoln4791.ecommerce.service

import com.lincoln4791.ecommerce.model.entities.toProfileResponse
import com.lincoln4791.ecommerce.model.enums.ApiStatusEnum
import com.lincoln4791.ecommerce.model.requests.EditProfileRequest
import com.lincoln4791.ecommerce.model.responses.BaseResponse
import com.lincoln4791.ecommerce.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val userRepo: UserRepository
) {

    fun getUser(authentication: Authentication) : ResponseEntity<Any> {
        val email = authentication.name
        val user = userRepo.findByEmail(email)?:return ResponseEntity.ok(BaseResponse(
            status_code=401,
            message= ApiStatusEnum.Failed.name,
            errors = "User not found",
            data =  null
        ))

        return ResponseEntity.ok(BaseResponse(
            status_code=200,
            message= ApiStatusEnum.Success.name,
            errors = null,
            data =  user.toProfileResponse()
        ))
    }

    fun editProfile(
        authentication: Authentication,
        editProfileRequest: EditProfileRequest
    ): ResponseEntity<Any> {

        val email = authentication.name

        val user = userRepo.findByEmail(email)
            ?: return ResponseEntity.status(401).body(
                BaseResponse(
                    status_code = 401,
                    message = ApiStatusEnum.Failed.name,
                    errors = "User not found",
                    data = null
                )
            )

        // 🔴 Phone uniqueness check
        editProfileRequest.phone?.let { newPhone ->
            val phoneExists = userRepo.existsByPhoneAndIdNot(newPhone, user.id)
            if (phoneExists) {
                return ResponseEntity.ok(
                    BaseResponse(
                        status_code = 409,
                        message = ApiStatusEnum.Failed.name,
                        errors = "Phone number already exists",
                        data = null
                    )
                )
            }
            user.phone = newPhone
        }

        // 🟢 Name update (no uniqueness needed)
        editProfileRequest.name?.let {
            user.name = it
        }

        val userUpdated = userRepo.save(user)

        return ResponseEntity.ok(
            BaseResponse(
                status_code = 200,
                message = ApiStatusEnum.Success.name,
                errors = null,
                data = userUpdated.toProfileResponse()
            )
        )
    }

}
