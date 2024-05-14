package com.api.auth.jwtUtil


import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(
    private val jwtBuilder: JwtBuilder,
    private val rsaKeyBuilder: RsaKeyBuilder
) {

    fun validateToken(token: String): Boolean {
        try {
            val rsaKey = rsaKeyBuilder.loadRsaKeyFromFile()
            val publicKey = rsaKey.toRSAPublicKey()

            val signedJWT = SignedJWT.parse(token)

            val verifier = RSASSAVerifier(publicKey)

            if (!signedJWT.verify(verifier)) {
                return false
            }

            val claims = signedJWT.jwtClaimsSet
            return Date().before(claims.expirationTime)

        } catch (e: Exception) {
            println("Error during token validation: ${e.message}")
            return false
        }
    }

    fun getClaimsFromToken(token: String): JWTClaimsSet? {
        try {
            val rsaKey = rsaKeyBuilder.loadRsaKeyFromFile()
            val publicKey = rsaKey.toRSAPublicKey()

            val signedJWT = SignedJWT.parse(token)
            val verifier = RSASSAVerifier(publicKey)

            if (signedJWT.verify(verifier)) {
                return signedJWT.jwtClaimsSet
            }
        } catch (e: Exception) {
            println("Error during token decoding: ${e.message}")
        }
        return null
    }
}