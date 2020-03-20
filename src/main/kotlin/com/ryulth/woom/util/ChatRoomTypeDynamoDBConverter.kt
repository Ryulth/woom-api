package com.ryulth.woom.util

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import com.ryulth.woom.dto.ChatRoomType

class ChatRoomTypeDynamoDBConverter : DynamoDBTypeConverter<String, ChatRoomType> {
    override fun convert(source: ChatRoomType): String {
        return source.name
    }

    override fun unconvert(source: String): ChatRoomType {
        return ChatRoomType.valueOf(source)
    }
}
