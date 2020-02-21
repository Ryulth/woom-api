package com.ryulth.woom.domain

import com.ryulth.woom.domain.model.Message
import com.ryulth.woom.domain.repository.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val dynamoDBSequenceGenerator: DynamoDBSequenceGenerator
) {
    fun generateSequenceNo(chatRoomId: Long) =
        dynamoDBSequenceGenerator.generateSequence("${Message.SEQUENCE_NAME}@$chatRoomId")

    fun save(message: Message): Message = messageRepository.save(message)
}
