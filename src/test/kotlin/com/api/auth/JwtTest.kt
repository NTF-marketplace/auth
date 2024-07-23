package com.api.auth


import com.api.auth.controller.dto.JwtRequest
import com.api.auth.jwtUtil.JwtBuilder
import com.api.auth.jwtUtil.RsaKeyBuilder
import com.api.auth.service.AuthService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.annotation.AccessType
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest
@ActiveProfiles("local")
class JwtTest(
    @Autowired private val rsaKeyBuilder: RsaKeyBuilder,
    @Autowired private val jwtBuilder: JwtBuilder,
    @Autowired private val authService: AuthService,
) {

    @Test
    fun createToken() {
//        val rsa = rsaKeyBuilder.generateRsaKey()
//        rsaKeyBuilder.saveRsaKeyToFile(rsa)

        val issuedAt = Instant.now()
        val expiresAt = issuedAt.plus(3, ChronoUnit.DAYS)


        val token = jwtBuilder.buildJwtToken(issuedAt,expiresAt,"asdasdasdasdasdasdasda")
        println("token : " + token)

    }

    @Test
    fun createToken1() {
        val request = JwtRequest(
            address = "test12345123"
        )
        authService.createToken(request).block()
    }


}