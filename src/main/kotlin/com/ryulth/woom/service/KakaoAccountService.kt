package com.ryulth.woom.service

import com.ryulth.woom.domain.KakaoUserService
import com.ryulth.woom.domain.UserService
import com.ryulth.woom.domain.model.KakaoUser
import com.ryulth.woom.domain.model.User
import com.ryulth.woom.domain.model.UserName
import com.ryulth.woom.dto.KakaoLoginRequest
import com.ryulth.woom.dto.KakaoRegisterRequest
import com.ryulth.woom.dto.LoginRequest
import com.ryulth.woom.dto.RegisterRequest
import com.ryulth.woom.dto.UserSession
import com.ryulth.woom.security.TokenProvider
import com.ryulth.woom.util.StringUtils
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service

@Service
class KakaoAccountService(
    private val userService: UserService,
    private val kakaoUserService: KakaoUserService,
    private val kakaoVerifyService: KakaoVerifyService,
    tokenProvider: TokenProvider
) : AccountService(tokenProvider) {

    companion object {
        private const val KAKAO_POSTFIX: String = "${StringUtils.WOOM_POSTFIX}@KAKAO"
    }

    override fun isExistAccount(registerRequest: RegisterRequest): Boolean {
        return kakaoUserService.existsByKakaoId((registerRequest as KakaoRegisterRequest).kakaoId)
    }

    override fun registerAccount(registerRequest: RegisterRequest): User {
        val kakaoRegisterRequest = registerRequest as KakaoRegisterRequest
        val user = userService.save(
            User(
                woomId = kakaoRegisterRequest.kakaoId + KAKAO_POSTFIX,
                loginType = kakaoRegisterRequest.loginType,
                userName = UserName(
                    firstName = "",
                    lastName = "",
                    nickName = ""
                ),
                interestedCategorySet = hashSetOf(),
                hasCategorySet = hashSetOf()
            )
        )

        val kakaoUser = KakaoUser(
            kakaoId = kakaoRegisterRequest.kakaoId,
            kakaoEmail = kakaoRegisterRequest.kakaoEmail,
            lastAccessToken = kakaoRegisterRequest.accessToken,
            userId = user.id!!
        )

        if (kakaoVerifyService.verifyAccessToken(kakaoUser, kakaoRegisterRequest.accessToken)) {
            kakaoUserService.save(kakaoUser)
        }

        return user
    }

    override fun verifyAccount(loginRequest: LoginRequest): UserSession {
        val kakaoLoginRequest = loginRequest as KakaoLoginRequest
        val kakaoUser = kakaoUserService.findByKakaoId(kakaoLoginRequest.kakaoId)
        if (kakaoVerifyService.verifyAccessToken(kakaoUser, kakaoLoginRequest.accessToken)) {
            val user = userService.findByUserId(kakaoUser.userId)
            return UserSession(
                userId = user.id!!,
                woomId = user.woomId,
                loginType = user.loginType
            )
        }
        throw BadCredentialsException("Apple token invalid")
    }
}
