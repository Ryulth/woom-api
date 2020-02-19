package com.ryulth.woom.domain.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "DynamoDBSequence")
data class DynamoDBSequence(
    @DynamoDBHashKey
    val id: String,

    @DynamoDBAttribute
    val seq: Long = 0
)
