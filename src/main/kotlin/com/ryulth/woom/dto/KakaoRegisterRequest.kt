package com.ryulth.woom.dto

import com.ryulth.woom.dto.LoginType.KAKAO

data class KakaoRegisterRequest(
    val kakaoId: String,
    val kakaoEmail: String,
    val accessToken: String,

    override val firstName: String,
    override val lastName: String,
    override val nickName: String
) : RegisterRequest(KAKAO)
