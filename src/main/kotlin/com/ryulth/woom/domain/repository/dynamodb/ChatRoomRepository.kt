package com.ryulth.woom.domain.repository.dynamodb

import com.ryulth.woom.domain.model.ChatRoom
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface ChatRoomRepository : CrudRepository<ChatRoom, Long>
