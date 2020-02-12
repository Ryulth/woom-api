package com.ryulth.woom.security

import com.ryulth.woom.dto.Token
import com.ryulth.woom.dto.UserSession
import org.springframework.stereotype.Service

@Service
interface TokenProvider {
    fun refreshToken(refreshToken: String): Token
    fun verifyToken(token: String, isAccess: Boolean): UserSession
    fun generatedToken(userSession: UserSession): Token
}