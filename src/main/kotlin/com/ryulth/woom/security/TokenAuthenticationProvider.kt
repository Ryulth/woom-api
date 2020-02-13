package com.ryulth.woom.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class TokenAuthenticationProvider(
    private val tokenProvider: TokenProvider
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication.principal as String
        return tokenProvider.verifyToken(token, true)
    }

    override fun supports(authentication: Class<*>?) = true
}
