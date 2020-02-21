package com.ryulth.woom.util

import com.ryulth.woom.dto.ChatRoomType
import com.ryulth.woom.dto.ChatRoomType.DIRECT
import com.ryulth.woom.dto.ChatRoomType.GROUP

object EnumUtils {
    fun calcChatRoomType(count: Int): ChatRoomType {
        return if (count == 2) DIRECT else if (count > 2) GROUP else throw IllegalArgumentException()
    }
}
