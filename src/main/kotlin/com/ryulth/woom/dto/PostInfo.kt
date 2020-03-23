package com.ryulth.woom.dto

import java.time.LocalDateTime

data class PostInfo(
    val postId: String,
    val authorId: Long,
    val title: String,
    val content: String,
    val hasCategorySet: Set<String>,
    val wantCategorySet: Set<String>,
    val location: String,
    val createdAt: LocalDateTime
)
