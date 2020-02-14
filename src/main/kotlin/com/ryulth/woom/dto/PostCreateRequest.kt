package com.ryulth.woom.dto

data class PostCreateRequest(
    val title: String,
    val content: String,
    val hasCategorySet: Set<String>,
    val wantCategorySet: Set<String>,
    val location: String
)