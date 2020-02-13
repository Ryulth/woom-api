package com.ryulth.woom.dto

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String
)
