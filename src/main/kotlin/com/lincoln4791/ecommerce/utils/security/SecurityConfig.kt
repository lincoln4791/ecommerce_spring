package com.lincoln4791.ecommerce.utils.security

import com.lincoln4791.ecommerce.utils.authUtils.CustomUserDetailsService
import com.lincoln4791.ecommerce.utils.authUtils.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter,
    private val userDetailsService: CustomUserDetailsService,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val authEntryPoint: CustomAuthEntryPoint
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    /*.requestMatchers("/auth/signup", "/auth/login").permitAll()   // PUBLIC
                    .anyRequest().authenticated()    */                            // PROTECTED
                    .requestMatchers("/auth/login", "/auth/signup","/auth/refresh").permitAll()

                    // Only admin can add product
                    //.requestMatchers(HttpMethod.POST, "/products/add").hasRole("ADMIN")

                    // All authenticated users can view
                    //.requestMatchers(HttpMethod.GET, "/products/**").authenticated()
                    .requestMatchers("/error", "/favicon.ico").permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it.accessDeniedHandler(accessDeniedHandler)   // For 403
                it.authenticationEntryPoint(authEntryPoint)   // For 401
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val dao = DaoAuthenticationProvider()
        dao.setUserDetailsService(userDetailsService)
        dao.setPasswordEncoder(passwordEncoder())
        return dao
    }
}
