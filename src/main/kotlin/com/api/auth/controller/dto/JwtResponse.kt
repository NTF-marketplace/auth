package com.api.auth.controller.dto

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
)
