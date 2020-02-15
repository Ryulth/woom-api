package com.ryulth.woom.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

data class CategoryInfo(
    @JsonInclude(NON_NULL)
    var categoryCode: String? = null,
    @JsonInclude(NON_NULL)
    val categoryInfos: MutableSet<CategoryInfo>? = null,
    @JsonInclude(NON_NULL)
    var userCount: Long? = null,
    @JsonInclude(NON_NULL)
    var postCount: Long? = null
)
