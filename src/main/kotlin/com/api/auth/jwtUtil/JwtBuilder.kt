package com.api.auth.jwtUtil

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JwtBuilder(
    private val rsaKeyBuilder: RsaKeyBuilder,
) {
    fun buildJwtToken(
        issuedAt: Instant,
        expiresAt: Instant,
        address: String
    ): String {

        val rsaKey = rsaKeyBuilder.loadRsaKeyFromFile()

        val claimsSet = JWTClaimsSet.Builder()
            .issueTime(Date.from(issuedAt))
            .notBeforeTime(Date.from(issuedAt))
            .expirationTime(Date.from(expiresAt))
            .claim("address", address)
            .build()

        return signJwt(claimsSet,rsaKey)
    }

    private fun signJwt(claimsSet: JWTClaimsSet, rsaKey: RSAKey): String {
        val signer: JWSSigner = RSASSASigner(rsaKey)
        val signedJWT = SignedJWT(
            JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.keyID).build(),
            claimsSet
        )
        signedJWT.sign(signer)
        return signedJWT.serialize()
    }

}