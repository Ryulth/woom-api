package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.UserChatRoom
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface UserChatRoomRepository : CrudRepository<UserChatRoom, Long> {
    fun existsByUserIdAndDirectChatRoomIdsIn(id: Long, chatRoomId: Long): Boolean
}
