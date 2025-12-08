package com.lincoln4791.ecommerce.utils.authUtils

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtUtils: JwtUtils,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (header != null && header.startsWith("Bearer ")) {

            try {
                val token = header.substring(7)
                val email = jwtUtils.extractEmail(token)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailsService.loadUserByUsername(email)

                    if (jwtUtils.validateToken(token)) {
                        val auth = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities
                        )
                        SecurityContextHolder.getContext().authentication = auth
                    }
                }
            }
            catch (ex: UsernameNotFoundException) {
                // user not found → do NOT throw → continue → results in 401
            } catch (ex: Exception) {
                // Any other JWT parsing error → ignore → 401
            }

        }

        filterChain.doFilter(request, response)
    }
}