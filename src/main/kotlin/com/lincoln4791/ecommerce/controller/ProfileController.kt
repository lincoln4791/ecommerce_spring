package com.lincoln4791.ecommerce.controller

import com.lincoln4791.ecommerce.model.requests.EditProfileRequest
import com.lincoln4791.ecommerce.service.ProfileService
import jakarta.validation.Valid
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profile")
class ProfileController(private val service: ProfileService) {

    @GetMapping
    fun getProfile(
        authentication: Authentication
    ) = service.getUser(authentication)


    @PostMapping("/edit")
    fun editProfile(
        authentication: Authentication,
        @Valid @RequestBody editProfileRequest : EditProfileRequest
    ) = service.editProfile(authentication,editProfileRequest)


}
