package com.api.auth.domain

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface RefreshTokenRepository : ReactiveCrudRepository<RefreshToken,Long> {
    fun findByWalletAddress(address:String): Mono<RefreshToken>
}