package com.ryulth.woom.dto

import org.springframework.security.authentication.AbstractAuthenticationToken

data class UserSession(
    val userId: Long,
    val woomId: String,
    val loginType: LoginType
) : AbstractAuthenticationToken(null) {
    init {
        this.isAuthenticated = false
    }

    override fun getCredentials() = null
    override fun getPrincipal() = null
}
