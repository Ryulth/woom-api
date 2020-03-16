package com.ryulth.woom.domain.repository.dynamodb

import com.ryulth.woom.domain.model.Message
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface MessageRepository : CrudRepository<Message, String>
