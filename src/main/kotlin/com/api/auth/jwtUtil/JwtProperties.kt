package com.api.auth.jwtUtil

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
     var rsaKeyPath: String? = null
}