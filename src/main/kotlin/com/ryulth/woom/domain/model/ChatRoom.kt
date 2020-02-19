package com.ryulth.woom.domain.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted
import com.ryulth.woom.dto.ChatRoomType
import com.ryulth.woom.util.LocalDateTimeDynamoDBConverter
import org.springframework.data.annotation.Transient
import java.time.LocalDateTime

@DynamoDBTable(tableName = "ChatRoom")
data class ChatRoom (
    @DynamoDBHashKey
    val id: Long,

    @DynamoDBAttribute
    val chatRoomType: ChatRoomType,

    @DynamoDBAttribute
    val brokerChannel: String,

    @DynamoDBAttribute
    val joinUserIds: MutableSet<Long>?,

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeDynamoDBConverter::class)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    /**
     * id generated 를 위한 key string
     */
    companion object {
        @Transient
        val SEQUENCE_NAME = "chatRoomSequence"
    }
}