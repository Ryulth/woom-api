package com.ryulth.woom.service

import com.ryulth.woom.domain.service.AppleUserService
import com.ryulth.woom.domain.service.UserService
import com.ryulth.woom.domain.model.AppleUser
import com.ryulth.woom.domain.model.User
import com.ryulth.woom.domain.model.UserImage
import com.ryulth.woom.domain.model.UserName
import com.ryulth.woom.dto.AppleLoginRequest
import com.ryulth.woom.dto.AppleRegisterRequest
import com.ryulth.woom.dto.LoginRequest
import com.ryulth.woom.dto.RegisterRequest
import com.ryulth.woom.dto.UserSession
import com.ryulth.woom.security.TokenProvider
import com.ryulth.woom.util.StringUtils.WOOM_POSTFIX
import mu.KLogging
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service

@Service
class AppleAccountService(
        private val userService: UserService,
        private val appleUserService: AppleUserService,
        private val appleVerifyService: AppleVerifyService,
        tokenProvider: TokenProvider
) : AccountService(tokenProvider) {

    companion object : KLogging() {
        private const val APPLE_POSTFIX: String = "$WOOM_POSTFIX@APPLE"
    }

    override fun isExistAccount(registerRequest: RegisterRequest): Boolean {
        return appleUserService.existsByAppleId((registerRequest as AppleRegisterRequest).appleId)
    }

    override fun registerAccount(registerRequest: RegisterRequest): User {
        val appleRegisterRequest = registerRequest as AppleRegisterRequest
        val user = userService.save(
            User(
                woomId = appleRegisterRequest.appleId + APPLE_POSTFIX,
                loginType = appleRegisterRequest.loginType,
                userName = UserName(
                    firstName = null,
                    lastName = null,
                    nickName = ""
                ),
                interestedCategorySet = hashSetOf(),
                hasCategorySet = hashSetOf(),
                userImage = UserImage(
                    null,
                    null
                )
            )
        )

        val appleUser = AppleUser(
            appleId = appleRegisterRequest.appleId,
            appleEmail = appleRegisterRequest.appleEmail,
            lastAccessToken = appleRegisterRequest.accessToken,
            userId = user.id!!
        )

        if (appleVerifyService.verifyAccessToken(appleUser, appleRegisterRequest.accessToken)) {
            appleUserService.save(appleUser)
        }

        return user
    }

    override fun verifyAccount(loginRequest: LoginRequest): UserSession {
        val appleLoginRequest = loginRequest as AppleLoginRequest
        val appleUser = appleUserService.findByAppleId(appleLoginRequest.appleId)
        if (appleVerifyService.verifyAccessToken(appleUser, appleLoginRequest.accessToken)) {
            val user = userService.findByUserId(appleUser.userId)
            return UserSession(
                userId = user.id!!,
                woomId = user.woomId,
                loginType = user.loginType
            )
        }
        throw BadCredentialsException("Apple token invalid")
    }
}
