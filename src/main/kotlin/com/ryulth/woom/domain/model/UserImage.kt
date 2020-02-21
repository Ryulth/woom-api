package com.ryulth.woom.domain.model

import javax.persistence.Embeddable
import org.hibernate.annotations.Formula

@Embeddable
data class UserImage(
    var thumbnailImageUrl: String?,
    var profileImageUrl: String?,
    /**
     * Embeddable 객체를 빈 객체로 초기화 시키기 위한 더미 필드.
     */
    @Formula("0")
    private val dummy: Int = 0
)
