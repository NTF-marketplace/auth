package com.api.auth.service


import com.api.auth.jwtUtil.JwtBuilder
import com.api.auth.controller.dto.JwtRequest
import com.api.auth.controller.dto.JwtResponse
import com.api.auth.domain.RefreshToken
import com.api.auth.domain.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class AuthService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtBuilder: JwtBuilder,
) {

    @Transactional
    fun createToken(request: JwtRequest): Mono<JwtResponse> {
        val issuedAt = Instant.now()
        val expiresAtAccess = issuedAt.plus(1, ChronoUnit.DAYS)

        val accessToken = jwtBuilder.buildJwtToken(issuedAt,expiresAtAccess,request.address)
        return findOrCreate(issuedAt,request.address).map {
            JwtResponse(accessToken,it)
        }
    }

    fun findOrCreate(issuedAt: Instant,address: String): Mono<String> {
        return refreshTokenRepository.findByWalletAddress(address)
            .switchIfEmpty (
                saveRefreshToken(issuedAt, address)
            ).map { it.refreshToken }
    }

    fun saveRefreshToken(issuedAt: Instant, address: String): Mono<RefreshToken> {
        val expiresAtRefresh = issuedAt.plus(5,ChronoUnit.DAYS)
        val refreshToken = jwtBuilder.buildJwtToken(issuedAt,expiresAtRefresh, address)

       return refreshTokenRepository.save(
           RefreshToken(refreshToken = refreshToken, walletAddress = address)
       )
    }
}