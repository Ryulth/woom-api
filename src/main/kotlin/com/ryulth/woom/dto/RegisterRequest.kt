package com.ryulth.woom.dto

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class RegisterRequest(
    @JsonIgnore val loginType: LoginType
) {
    abstract val firstName: String
    abstract val lastName: String
    abstract val nickName: String
}
