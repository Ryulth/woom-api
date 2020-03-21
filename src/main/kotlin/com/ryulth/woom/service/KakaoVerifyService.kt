package com.ryulth.woom.service

import com.ryulth.woom.domain.model.KakaoUser
import com.ryulth.woom.domain.service.KakaoUserService
import com.ryulth.woom.dto.KakaoTokenInfoResponse
import mu.KLogging
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service

@Service
class KakaoVerifyService(
    private val kakaoApiService: KakaoApiService,
    private val kakaoUserService: KakaoUserService
) {
    companion object : KLogging()
    fun verifyAccessToken(kakaoUser: KakaoUser, accessToken: String): Boolean {
        if (kakaoUser.lastAccessToken.isNotEmpty() && kakaoUser.lastAccessToken == accessToken) {
            logger.info { "Last accessToken 과 동일합니다." }
            return true
        }
        val kakaoTokenInfoResponse: KakaoTokenInfoResponse = kakaoApiService.getKakaoTokenInfo(accessToken)
        val accessKakaoId = kakaoTokenInfoResponse.id.toString()
        if (accessKakaoId == kakaoUser.kakaoId) {
            kakaoUser.lastAccessToken = accessToken
            kakaoUserService.save(kakaoUser)
            return true
        }
        throw BadCredentialsException("Request kakao token is invalid")
    }
}
