package com.ryulth.woom.dto

import com.ryulth.woom.dto.LoginType.APPLE

data class AppleLoginRequest(
    val appleId: String,
    val accessToken: String
) : LoginRequest(APPLE)
