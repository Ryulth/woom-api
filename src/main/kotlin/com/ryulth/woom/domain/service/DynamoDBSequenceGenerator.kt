package com.ryulth.woom.domain.service

import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.PrimaryKey
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.amazonaws.services.dynamodbv2.model.ReturnValue
import org.springframework.stereotype.Service

@Service
class DynamoDBSequenceGenerator(
    private val dynamoDB: DynamoDB
) {
    companion object {
        const val SEQUENCE_TABLE_NAME = "DynamoDBSequence"
        const val SEQ_ATTRIBUTE = "seq"
    }

    fun generateSequence(seqName: String): Long {
        val updateItemOutcome = dynamoDB.getTable(SEQUENCE_TABLE_NAME).updateItem(UpdateItemSpec().apply {
            withPrimaryKey(PrimaryKey("id", seqName))
            withUpdateExpression("ADD $SEQ_ATTRIBUTE :incr")
            withValueMap(ValueMap().withLong(":incr", 1L))
            withReturnValues(ReturnValue.UPDATED_NEW)
        })
        return updateItemOutcome.item.getLong(SEQ_ATTRIBUTE)
    }
}
