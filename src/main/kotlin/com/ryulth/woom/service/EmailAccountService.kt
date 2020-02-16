package com.ryulth.woom.service

import com.ryulth.woom.domain.EmailUserService
import com.ryulth.woom.domain.UserService
import com.ryulth.woom.domain.model.EmailUser
import com.ryulth.woom.domain.model.User
import com.ryulth.woom.domain.model.UserImage
import com.ryulth.woom.domain.model.UserName
import com.ryulth.woom.dto.EmailLoginRequest
import com.ryulth.woom.dto.EmailRegisterRequest
import com.ryulth.woom.dto.LoginRequest
import com.ryulth.woom.dto.RegisterRequest
import com.ryulth.woom.dto.UserSession
import com.ryulth.woom.security.TokenProvider
import com.ryulth.woom.util.StringUtils.WOOM_POSTFIX
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Service

@Service
class EmailAccountService(
    private val userService: UserService,
    private val emailUserService: EmailUserService,
    tokenProvider: TokenProvider
) : AccountService(tokenProvider) {

    companion object {
        private val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        private const val EMAIL_POSTFIX: String = "$WOOM_POSTFIX@EMAIL"
    }

    override fun isExistAccount(registerRequest: RegisterRequest): Boolean {
        return emailUserService.existsByEmail((registerRequest as EmailRegisterRequest).email)
    }

    override fun registerAccount(registerRequest: RegisterRequest): User {
        val emailRegisterRequest = registerRequest as EmailRegisterRequest
        val user = userService.save(
            User(
                woomId = emailRegisterRequest.email + EMAIL_POSTFIX,
                publicEmail = emailRegisterRequest.email,
                loginType = emailRegisterRequest.loginType,
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

        emailUserService.save(
            EmailUser(
                email = emailRegisterRequest.email,
                password = passwordEncoder.encode(emailRegisterRequest.password),
                userId = user.id!!
            )
        )

        return user
    }

    override fun verifyAccount(loginRequest: LoginRequest): UserSession {
        val emailLoginRequest = loginRequest as EmailLoginRequest
        val emailUser = emailUserService.findByEmail(emailLoginRequest.email)
        if (equalsPassword(emailLoginRequest.password, emailUser.password)) {
            val user = userService.findByUserId(emailUser.userId)
            return UserSession(
                userId = user.id!!,
                woomId = user.woomId,
                loginType = user.loginType
            )
        }
        throw BadCredentialsException("Password not match")
    }

    fun equalsPassword(requestPassword: String, originPassword: String): Boolean {
        return passwordEncoder.matches(requestPassword, originPassword)
    }
}
