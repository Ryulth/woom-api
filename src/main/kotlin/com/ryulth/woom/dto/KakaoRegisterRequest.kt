package com.ryulth.woom.dto

import com.ryulth.woom.dto.LoginType.KAKAO

data class KakaoRegisterRequest(
    val kakaoId: String,
    val kakaoEmail: String,
    val accessToken: String

) : RegisterRequest(KAKAO)
