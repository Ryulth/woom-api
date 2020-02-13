package com.ryulth.woom.dto

import com.ryulth.woom.dto.LoginType.EMAIL

data class EmailLoginRequest(
    val email: String,
    val password: String
) : LoginRequest(EMAIL)
