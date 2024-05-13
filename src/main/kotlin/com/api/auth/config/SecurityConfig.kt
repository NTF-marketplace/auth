package com.api.auth.config

import com.api.auth.security.RefreshTokenValidationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableReactiveMethodSecurity
class SecurityConfig(
    private val refreshTokenValidationFilter: RefreshTokenValidationFilter
) {


    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().requestMatchers("/v1/auth") }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/v1/auth").permitAll()
                it.requestMatchers("/v1/auth/reissue").authenticated()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(refreshTokenValidationFilter, UsernamePasswordAuthenticationFilter::class.java)


        return http.build()
    }
}