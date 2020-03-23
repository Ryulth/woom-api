package com.ryulth.woom.service

import com.ryulth.woom.dto.KakaoTokenInfoResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class KakaoApiService(
    private val webClient: WebClient
) {
    companion object {
        private const val KAKAO_API_BASE_URL = "https://kapi.kakao.com"
        private const val KAKAO_TOKEN_INFO_URL =
            "$KAKAO_API_BASE_URL/v1/user/access_token_info"
        private const val KAKAO_USER_PROFILE_URL = "$KAKAO_API_BASE_URL/v2/user/me"
    }
    fun getKakaoTokenInfo(accessToken: String): KakaoTokenInfoResponse {
        return webClient.get()
            .uri(KAKAO_TOKEN_INFO_URL)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .bodyToMono(KakaoTokenInfoResponse::class.java)
            .block()!!
    }
}
