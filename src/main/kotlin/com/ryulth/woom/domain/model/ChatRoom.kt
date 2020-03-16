package com.ryulth.woom.domain.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted
import com.ryulth.woom.dto.ChatRoomType
import com.ryulth.woom.dto.ChatRoomType.ETC
import com.ryulth.woom.util.ChatRoomTypeDynamoDBConverter
import com.ryulth.woom.util.LocalDateTimeDynamoDBConverter
import java.time.LocalDateTime
import org.springframework.data.annotation.Transient

@DynamoDBTable(tableName = "ChatRoom")
data class ChatRoom(
    @DynamoDBHashKey
    var id: Long = 0,

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = ChatRoomTypeDynamoDBConverter::class)
    var chatRoomType: ChatRoomType = ETC,

    @DynamoDBAttribute
    var brokerChannel: String = "",

    @DynamoDBAttribute
    var joinUserIds: MutableSet<Long> = mutableSetOf(),

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeDynamoDBConverter::class)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeDynamoDBConverter::class)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    /**
     * id generated 를 위한 key string
     */
    companion object {
        @Transient
        val SEQUENCE_NAME = "chatRoomSequence"
    }
}
