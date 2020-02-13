package com.ryulth.woom.domain.model

import javax.persistence.Embeddable

@Embeddable
data class UserName(
    var firstName: String,
    var lastName: String,
    var nickName: String
)
