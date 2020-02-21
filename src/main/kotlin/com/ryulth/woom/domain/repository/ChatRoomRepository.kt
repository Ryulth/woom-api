package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.ChatRoom
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface ChatRoomRepository : CrudRepository<ChatRoom, Long>
