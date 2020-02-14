package com.ryulth.woom.domain.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Category(
    @Id
    val categoryCode: String,

    /**
     * 상위 카테고리 code 이다.
     * null 이면 최상위
     */
    val upperCategoryCode: String?,
    val description: String?,
    val userCount: Long,
    val postCount: Long
)