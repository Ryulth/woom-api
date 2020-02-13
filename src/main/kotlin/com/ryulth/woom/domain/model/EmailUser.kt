package com.ryulth.woom.domain.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class EmailUser(
    @Id
    val email: String,
    var password: String,
    val userId: Long
)
