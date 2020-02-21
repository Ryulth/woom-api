package com.ryulth.woom.dto


data class ChatRoomInfo(
    val chatRoomId: Long,
    val chatRoomType: ChatRoomType,
    val brokerChannelUrl: String,
    val joinUserIds: Set<Long>
)