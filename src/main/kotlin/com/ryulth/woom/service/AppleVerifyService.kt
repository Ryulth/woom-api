package com.ryulth.woom.service

import com.auth0.jwk.JwkException
import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.InvalidClaimException
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.ryulth.woom.domain.model.AppleUser
import java.security.interfaces.RSAPublicKey
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service

@Service
class AppleVerifyService(
    @Value("\${apple.clientId:woom}")
    private val clientId: String
) {
    companion object {
        private const val APPLE_TOKEN_ISSUER = "https://appleid.apple.com"
        private const val APPLE_PUBLIC_KEYS_URL = "https://appleid.apple.com/auth/keys"
        private val APPLE_PUBLIC_JWK_PROVIDER = UrlJwkProvider(Companion.APPLE_PUBLIC_KEYS_URL)
    }

    fun verifyAccessToken(appleUser: AppleUser, accessToken: String): Boolean {
        if (appleUser.lastAccessToken == accessToken) {
            return true
        }
        try {
            val decodedJWT = JWT.decode(accessToken)
            val jwk = Companion.APPLE_PUBLIC_JWK_PROVIDER[decodedJWT.keyId]
            val algorithm =
                Algorithm.RSA256(jwk.publicKey as RSAPublicKey, null)
            val verifier = JWT.require(algorithm)
                .withIssuer(Companion.APPLE_TOKEN_ISSUER)
                // .withAudience(clientId)
                .build()
            verifier.verify(decodedJWT)
        } catch (e: JwkException) {
            throw BadCredentialsException("Request apple token invalid")
        } catch (e: TokenExpiredException) {
            throw BadCredentialsException("Request apple token invalid")
        } catch (e: JWTDecodeException) {
            throw BadCredentialsException("Request apple token invalid")
        } catch (e: InvalidClaimException) {
            throw BadCredentialsException("Request apple token invalid")
        }
        appleUser.lastAccessToken = accessToken
        return true
    }
}
