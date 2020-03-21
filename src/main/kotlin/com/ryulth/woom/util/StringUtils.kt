package com.ryulth.woom.util

object StringUtils {
    const val WOOM_PREFIX = "WOOM@"
    const val WOOM_POSTFIX = "@WOOM"
    const val CHAT_ROOM_PREFIX = "/chat/room"
    const val BROKER_PREFIX = "BROKER@"
    const val EMPTY_STRING = ""

    fun formatBrokerChannel(chatRoomId: Long) =
        "$CHAT_ROOM_PREFIX/$BROKER_PREFIX$chatRoomId@${System.currentTimeMillis()}"
}
