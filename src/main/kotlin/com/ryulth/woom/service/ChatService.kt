package com.ryulth.woom.service

import com.google.common.collect.Lists
import com.ryulth.woom.domain.service.ChatRoomService
import com.ryulth.woom.domain.service.UserChatRoomService
import com.ryulth.woom.domain.service.UserService
import com.ryulth.woom.domain.model.ChatRoom
import com.ryulth.woom.domain.model.UserChatRoom
import com.ryulth.woom.dto.ChatRoomInfo
import com.ryulth.woom.dto.ChatRoomType
import com.ryulth.woom.dto.ChatRoomType.DIRECT
import com.ryulth.woom.dto.ChatRoomType.GROUP
import com.ryulth.woom.dto.ChatRoomsInfos
import com.ryulth.woom.dto.CreateChatRoomRequest
import com.ryulth.woom.util.EnumUtils.calcChatRoomType
import com.ryulth.woom.util.StringUtils.formatBrokerChannel
import org.springframework.beans.factory.annotation.Value
import java.util.function.Consumer
import java.util.stream.Collectors
import org.springframework.stereotype.Service

@Service
class ChatService(
        private val userSessionService: UserSessionService,
        private val chatRoomService: ChatRoomService,
        private val userService: UserService,
        private val userChatRoomService: UserChatRoomService,
        @Value("\${mqtt.url}")
    val brokerURL: String
) {
    companion object {
        fun chatRoomToChatRoomInfo(chatRoom: ChatRoom, brokerURL: String) = ChatRoomInfo(
            chatRoomId = chatRoom.id,
            chatRoomType = chatRoom.chatRoomType,
            brokerChannelUrl = "$brokerURL/${chatRoom.brokerChannel}",
            joinUserIds = chatRoom.joinUserIds
        )
        fun addChatRoomId(userChatRoom: UserChatRoom, chatRoomId: Long, chatRoomType: ChatRoomType?) {
            when (chatRoomType) {
                DIRECT -> userChatRoom.directChatRoomIds.add(chatRoomId)
                GROUP -> userChatRoom.groupChatRoomIds.add(chatRoomId)
                else -> throw IllegalArgumentException()
            }
        }
    }

    fun getChatRooms(): ChatRoomsInfos {
        val userChatRoom = userChatRoomService.findByUserId(userSessionService.getCurrentUserSession().userId)
        val chatRoomIds = userChatRoom.directChatRoomIds + userChatRoom.groupChatRoomIds
        return ChatRoomsInfos(
            chatRoomInfos = chatRoomService.findAllByChatRoomIds(chatRoomIds)
                .map { chatRoomToChatRoomInfo(it, brokerURL) }.toList()
        )
    }

    fun createChatRoom(createChatRoomRequest: CreateChatRoomRequest): ChatRoom {
        val requestJoinUserIds: MutableList<Long> =
            Lists.newArrayList(createChatRoomRequest.joinUserIds)
        val userId: Long = userSessionService.getCurrentUserSession().userId
        requestJoinUserIds.add(userId)
        val joinUserIds = requestJoinUserIds.stream().distinct().collect(Collectors.toSet())

        if (userService.countByUserIds(joinUserIds) != joinUserIds.size.toLong()) {
            throw IllegalArgumentException("Undefined user id include")
        }

        val chatRoomType = calcChatRoomType(joinUserIds.size)
        var userChatRooms: List<UserChatRoom> = userChatRoomService.findAllByUserIds(joinUserIds)

        // document 가 없는 유저가 존재 == 채팅방이 없는 유저가 존재 업데이트 누락 방지
        if (userChatRooms.size != joinUserIds.size) {
            joinUserIds.forEach(userChatRoomService::saveIfNotExist)
            userChatRooms = userChatRoomService.findAllByUserIds(joinUserIds)
        }

        return existDirectChatRoom(userChatRooms, chatRoomType) ?: saveNewUserChatRooms(
            userChatRooms,
            joinUserIds,
            chatRoomType
        )
    }

    private fun saveNewUserChatRooms(
        userChatRooms: List<UserChatRoom>,
        joinUserIds: Set<Long>,
        chatRoomType: ChatRoomType
    ): ChatRoom {
        val chatRoomId = chatRoomService.generateSequenceId()
        userChatRooms.forEach(Consumer<UserChatRoom> { userChatRoom: UserChatRoom ->
            addChatRoomId(userChatRoom, chatRoomId, chatRoomType)
        })
        userChatRoomService.saveAll(userChatRooms)

        return chatRoomService.save(
            ChatRoom(
                id = chatRoomId,
                brokerChannel = formatBrokerChannel(chatRoomId),
                chatRoomType = chatRoomType,
                joinUserIds = joinUserIds.toMutableSet()
            )
        )
    }

    private fun existDirectChatRoom(userChatRooms: List<UserChatRoom>, chatRoomType: ChatRoomType): ChatRoom? {
        // direct 면 2명 이다. 하지만 한번더 체크
        if (chatRoomType === ChatRoomType.DIRECT || userChatRooms.size == 2) {
            val tempUserChatRoomIds = userChatRooms[0].directChatRoomIds.toMutableSet()
            val tempPeerChatRoomIds = userChatRooms[1].directChatRoomIds.toMutableSet()

            tempUserChatRoomIds.retainAll(tempPeerChatRoomIds)
            if (tempUserChatRoomIds.isNotEmpty()) {
                return try {
                    chatRoomService.findByChatRoomId(tempUserChatRoomIds.elementAt(0))
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
        }
        return null
    }
}
