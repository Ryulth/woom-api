package com.ryulth.woom.domain.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.ryulth.woom.dto.ChatRoomType
import com.ryulth.woom.dto.ChatRoomType.DIRECT
import com.ryulth.woom.dto.ChatRoomType.GROUP

@DynamoDBTable(tableName = "UserChatRoom")
data class UserChatRoom(
    /**
     * userId 와 매핑된다
     */
    @DynamoDBHashKey
    var userId: Long = 0,

    @DynamoDBAttribute
    var directChatRoomIds: MutableSet<Long> = mutableSetOf(),

    @DynamoDBAttribute
    var groupChatRoomIds: MutableSet<Long> = mutableSetOf()
) {
    fun addChatRoomId(chatRoomId: Long, chatRoomType: ChatRoomType?) {
        when (chatRoomType) {
            DIRECT -> directChatRoomIds.add(chatRoomId)
            GROUP -> groupChatRoomIds.add(chatRoomId)
            else -> throw IllegalArgumentException()
        }
    }

    fun removeChatRoomId(chatRoomId: Long, chatRoomType: ChatRoomType?) {
        when (chatRoomType) {
            DIRECT -> directChatRoomIds.remove(chatRoomId)
            GROUP -> groupChatRoomIds.remove(chatRoomId)
            else -> throw IllegalArgumentException()
        }
    }
}
