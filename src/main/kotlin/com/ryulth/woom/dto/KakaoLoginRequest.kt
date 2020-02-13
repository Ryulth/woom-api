package com.ryulth.woom.dto

import com.ryulth.woom.dto.LoginType.KAKAO

data class KakaoLoginRequest(
    val kakaoId: String,
    val accessToken: String
) : LoginRequest(KAKAO)
