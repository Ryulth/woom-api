package com.ryulth.woom.dto

import com.ryulth.woom.dto.LoginType.APPLE

data class AppleRegisterRequest(
    val appleId: String,
    val appleEmail: String,
    val accessToken: String

) : RegisterRequest(APPLE)
