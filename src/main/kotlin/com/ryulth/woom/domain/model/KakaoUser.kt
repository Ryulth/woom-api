package com.ryulth.woom.domain.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class KakaoUser(
    /**
     * 카카오 - 앱 사이의 부여되는 고유한 id
     */
    @Id
    val kakaoId: String,
    val kakaoEmail: String,
    var lastAccessToken: String,
    val userId: Long
)
