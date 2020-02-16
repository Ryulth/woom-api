package com.ryulth.woom.dto

data class UserInfo(
    val woomId: String,
    val loginType: LoginType,

    val firstName: String?,
    val lastName: String?,
    val nickName: String,

    val interestedCategorySet: Set<String>,
    val hasCategorySet: Set<String>,

    val thumbnailImageUrl: String?,
    val profileImageUrl: String?
)
