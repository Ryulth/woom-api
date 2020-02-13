package com.ryulth.woom.dto

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class LoginRequest(
    @JsonIgnore val loginType: LoginType
)
