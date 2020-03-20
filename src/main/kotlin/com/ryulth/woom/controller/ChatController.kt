package com.ryulth.woom.controller

import com.ryulth.woom.domain.model.ChatRoom
import com.ryulth.woom.domain.model.Message
import com.ryulth.woom.dto.ChatRoomsInfos
import com.ryulth.woom.dto.CreateChatRoomRequest
import com.ryulth.woom.dto.MessageRequest
import com.ryulth.woom.service.ChatMessageService
import com.ryulth.woom.service.ChatService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatService: ChatService,
    private val chatMessageService: ChatMessageService
) {
    @ApiOperation(value = "내 채팅 방정보들 API")
    @GetMapping("/rooms")
    fun getChatRooms(): ResponseEntity<ChatRoomsInfos> = ResponseEntity.ok(chatService.getChatRooms())

    @ApiOperation(value = "채팅방 만들기")
    @PostMapping("/room")
    fun createChatRoom(@RequestBody createChatRoomRequest: CreateChatRoomRequest): ResponseEntity<ChatRoom> {
        return ResponseEntity.ok(chatService.createChatRoom(createChatRoomRequest))
    }

    @ApiOperation(value = "채팅방 메시지 보내")
    @PostMapping("/rooms/{chatRoomId}/message")
    fun sendMessage(
        @PathVariable("chatRoomId") chatRoomId: Long,
        @RequestBody messageRequest: MessageRequest
    ): ResponseEntity<Message> {
        return ResponseEntity.ok(chatMessageService.sendMessage(chatRoomId, messageRequest))
    }

    @ApiOperation(value = "채팅방 메시지")
    @GetMapping("/rooms/{chatRoomId}/messages")
    fun getMessages(
        @PathVariable("chatRoomId") chatRoomId: Long,
        @RequestBody messageRequest: MessageRequest
    ): ResponseEntity<Message> {
        return ResponseEntity.ok(chatMessageService.sendMessage(chatRoomId, messageRequest))
    }
}
