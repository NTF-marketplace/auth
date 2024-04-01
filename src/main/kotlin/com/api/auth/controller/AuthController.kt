package com.api.auth.controller

import com.api.auth.controller.dto.JwtRequest
import com.api.auth.controller.dto.JwtResponse
import com.api.auth.service.AuthService
import com.nimbusds.oauth2.sdk.TokenRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping
    fun createToken(@RequestBody request: JwtRequest): Mono<JwtResponse> {
        return authService.createToken(request)
    }
}