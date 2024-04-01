package com.api.auth.domain

import org.springframework.data.annotation.Id

class RefreshToken(
    @Id val id: Long? = null,
    val walletAddress: String,
    val refreshToken: String,
) {
}