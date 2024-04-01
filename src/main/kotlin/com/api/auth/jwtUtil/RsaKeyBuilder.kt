package com.api.auth.jwtUtil

import com.nimbusds.jose.jwk.RSAKey
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Component
class RsaKeyBuilder(
    private val jwtProperties: JwtProperties,
) {

    fun generateRsaKey(): RSAKey {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair: KeyPair = keyPairGenerator.generateKeyPair()

        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey

        return RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(generateKeyId())
            .build()
    }

    fun generateKeyId(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun saveRsaKeyToFile(rsaKey: RSAKey) {
        val rsaKeyPath = jwtProperties.rsaKeyPath ?: throw IllegalStateException("RSA key path not configured")
        val json = rsaKey.toJSONString()
        Files.write(Paths.get(rsaKeyPath), json.toByteArray())
    }

    fun loadRsaKeyFromFile(): RSAKey {
        val rsaKeyPath = jwtProperties.rsaKeyPath ?: throw IllegalStateException("RSA key path not configured")
        val json = String(Files.readAllBytes(Paths.get(rsaKeyPath)))
        return RSAKey.parse(json)
    }
}