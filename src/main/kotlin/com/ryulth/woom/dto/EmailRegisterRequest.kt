package com.ryulth.woom.dto

import com.ryulth.woom.dto.LoginType.EMAIL

data class EmailRegisterRequest(
    val email: String,
    val password: String
) : RegisterRequest(EMAIL)
