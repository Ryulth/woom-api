package com.ryulth.woom.domain.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted
import com.ryulth.woom.util.LocalDateTimeDynamoDBConverter
import java.time.LocalDateTime

/**
 * DynamoDB lib 안에서 원래 setter 를 이용해서 작업을 해서 var 선언 해야한다.
 * jpa 처럼 생성자용 플러그인이 없어서을 기본값 선언해서 인스턴스화 시킨다.
 */
@DynamoDBTable(tableName = "Post")
data class PostComment(
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    var id: String? = null,

    @DynamoDBAttribute
    var postId: String = "",

    @DynamoDBAttribute
    var authorId: Long = 0,

    @DynamoDBAttribute
    var content: String = "",

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeDynamoDBConverter::class)
    var createdAt: LocalDateTime = LocalDateTime.now()
)
