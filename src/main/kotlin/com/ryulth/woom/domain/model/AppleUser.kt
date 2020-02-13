package com.ryulth.woom.domain.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class AppleUser(
    @Id
    val appleId: String,
    val appleEmail: String,
    var lastAccessToken: String,
    val userId: Long
)
