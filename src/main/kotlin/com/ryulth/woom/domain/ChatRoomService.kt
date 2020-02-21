package com.ryulth.woom.domain

import com.ryulth.woom.domain.model.ChatRoom
import com.ryulth.woom.domain.repository.ChatRoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val dynamoDBSequenceGenerator: DynamoDBSequenceGenerator
) {
    fun generateSequenceId() = dynamoDBSequenceGenerator.generateSequence(ChatRoom.SEQUENCE_NAME)

    fun findAllByChatRoomIds(chatRoomIds: Set<Long>): List<ChatRoom> =
        chatRoomRepository.findAllById(chatRoomIds) as List<ChatRoom>

    fun findByChatRoomId(chatRoomId: Long): ChatRoom =
        chatRoomRepository.findByIdOrNull(chatRoomId) ?: throw IllegalArgumentException()

    fun save(chatRoom: ChatRoom) = chatRoomRepository.save(chatRoom)
}
