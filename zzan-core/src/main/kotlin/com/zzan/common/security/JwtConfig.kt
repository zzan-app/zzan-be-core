package com.zzan.common.security

import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.nio.charset.StandardCharsets
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfig(
    private val jwtProperties: JwtProperties,
) {
    @Bean
    fun jwtEncoder(): JwtEncoder {
        val secret = SecretKeySpec(
            jwtProperties.secretKey.toByteArray(
                StandardCharsets.UTF_8
            ), SECRET_KEY_ALGORITHM
        )
        val immutableSecret = ImmutableSecret<SecurityContext>(secret)
        return NimbusJwtEncoder(immutableSecret)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secret = SecretKeySpec(
            jwtProperties.secretKey.toByteArray(
                StandardCharsets.UTF_8
            ), SECRET_KEY_ALGORITHM
        )
        return NimbusJwtDecoder.withSecretKey(secret).macAlgorithm(MacAlgorithm.HS256).build()
    }

    companion object {
        private const val SECRET_KEY_ALGORITHM = "HmacSHA256"
    }
}
