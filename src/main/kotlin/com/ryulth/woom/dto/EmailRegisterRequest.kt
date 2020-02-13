package com.ryulth.woom.dto

import com.ryulth.woom.dto.LoginType.EMAIL

data class EmailRegisterRequest(
    val email: String,
    val password: String,

    override val firstName: String,
    override val lastName: String,
    override val nickName: String
) : RegisterRequest(EMAIL)
