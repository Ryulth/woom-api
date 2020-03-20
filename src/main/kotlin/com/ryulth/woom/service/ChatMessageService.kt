package com.ryulth.woom.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ryulth.woom.config.MQTTConfig.MQTTGateway
import com.ryulth.woom.domain.model.Message
import com.ryulth.woom.domain.service.ChatRoomService
import com.ryulth.woom.domain.service.MessageService
import com.ryulth.woom.dto.MessageRequest
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class ChatMessageService(
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    private val mqttGateway: MQTTGateway,
    private val messageService: MessageService,
    private val chatRoomService: ChatRoomService,
    private val userSessionService: UserSessionService
) {
    companion object {
        private val objectMapper = jacksonObjectMapper()
    }

    fun sendMessage(chatRoomId: Long, messageRequest: MessageRequest): Message {
        val chatRoom = chatRoomService.findByChatRoomId(chatRoomId)
        val userSession = userSessionService.getCurrentUserSession()
        val brokerChannel = chatRoom.brokerChannel
        val content = messageRequest.content
        val message = Message(
            authorId = userSession.userId,
            brokerChannel = brokerChannel,
            chatRoomId = chatRoomId,
            sequenceNo = messageService.generateSequenceNo(chatRoomId),
            content = content,
            createdAt = LocalDateTime.now()
        )
        messageService.save(message)
        mqttGateway.publish(brokerChannel, objectMapper.writeValueAsString(message))
        return message
    }
}
