package com.ryulth.woom.dto

import java.time.LocalDateTime

data class PostCommentInfo(
    val postCommentId: String,
    val postId: String,
    val authorId: Long,
    val content: String,
    val createdAt: LocalDateTime
)
