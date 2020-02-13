package com.ryulth.woom.service

import com.ryulth.woom.domain.model.User
import com.ryulth.woom.dto.LoginRequest
import com.ryulth.woom.dto.RegisterRequest
import com.ryulth.woom.dto.Token
import com.ryulth.woom.dto.UserSession
import com.ryulth.woom.security.TokenProvider
import com.ryulth.woom.util.AccountAlreadyExistException

abstract class AccountService(
    private val tokenProvider: TokenProvider
) {
    fun register(registerRequest: RegisterRequest): Token {
        if (isExistAccount(registerRequest)) {
            throw AccountAlreadyExistException("Account Duplicated")
        }
        val user: User = registerAccount(registerRequest)
        return tokenProvider.generatedToken(
            UserSession(
                userId = user.id!!,
                woomId = user.woomId,
                loginType = user.loginType
            )
        )
    }

    protected abstract fun isExistAccount(registerRequest: RegisterRequest): Boolean

    protected abstract fun registerAccount(registerRequest: RegisterRequest): User

    fun login(loginRequest: LoginRequest): Token {
        return tokenProvider.generatedToken(verifyAccount(loginRequest))
    }

    protected abstract fun verifyAccount(loginRequest: LoginRequest): UserSession
}
