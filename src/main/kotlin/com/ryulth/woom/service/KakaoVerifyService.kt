package com.ryulth.woom.service

import com.ryulth.woom.domain.model.KakaoUser
import com.ryulth.woom.dto.KakaoTokenInfoResponse
import mu.KLogging
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service

@Service
class KakaoVerifyService(
    private val kakaoApiService: KakaoApiService
) {
    companion object : KLogging()
    fun verifyAccessToken(kakaoUser: KakaoUser, accessToken: String?): Boolean {
        if (kakaoUser.lastAccessToken == accessToken) {
            logger.info { "Last accessToken 과 동일합니다." }
            return true
        }
        val kakaoTokenInfoResponse: KakaoTokenInfoResponse = kakaoApiService.getKakaoTokenInfo(accessToken!!)
        val accessKakaoId = kakaoTokenInfoResponse.id.toString()
        if (accessKakaoId == kakaoUser.kakaoId) {
            kakaoUser.lastAccessToken = accessToken
            return true
        }
        throw BadCredentialsException("Request kakao token is invalid")
    }
}