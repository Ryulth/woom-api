package com.ryulth.woom.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

data class CategoryInfo(
    @JsonInclude(NON_NULL)
    var categoryCode: String?,
    @JsonInclude(NON_NULL)
    val categoryInfos: MutableSet<CategoryInfo>?
)