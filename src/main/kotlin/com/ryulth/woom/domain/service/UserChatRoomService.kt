package com.ryulth.woom.domain.service

import com.ryulth.woom.domain.model.UserChatRoom
import com.ryulth.woom.domain.repository.dynamodb.UserChatRoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserChatRoomService(
    private val userChatRoomRepository: UserChatRoomRepository
) {
    fun findByUserId(userId: Long) = userChatRoomRepository.findByIdOrNull(userId) ?: throw IllegalArgumentException()

    fun findAllByUserIds(userIds: Set<Long>): List<UserChatRoom> =
        userChatRoomRepository.findAllById(userIds) as List<UserChatRoom>

    fun isExistDirectChatRoom(userId: Long, chatRoomId: Long) =
        userChatRoomRepository.existsByUserIdAndDirectChatRoomIdsIn(userId, chatRoomId)

    fun save(userChatRoom: UserChatRoom): UserChatRoom = userChatRoomRepository.save(userChatRoom)

    fun saveAll(userChatRooms: List<UserChatRoom>) = userChatRoomRepository.saveAll(userChatRooms)

    fun saveIfNotExist(userId: Long) {
        if (!userChatRoomRepository.existsById(userId)) {
            userChatRoomRepository.save(
                UserChatRoom(
                    userId = userId,
                    directChatRoomIds = mutableSetOf(0L),
                    groupChatRoomIds = mutableSetOf(0L)
                )
            )
        }
    }
}
