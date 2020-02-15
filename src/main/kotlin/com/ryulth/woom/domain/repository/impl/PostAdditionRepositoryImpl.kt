package com.ryulth.woom.domain.repository.impl

import com.ryulth.woom.domain.model.Post
import com.ryulth.woom.domain.repository.PostAdditionRepository
import org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate

class PostAdditionRepositoryImpl(
    private val dynamoDBTemplate: DynamoDBTemplate
) : PostAdditionRepository {
    override fun findAllByCategoryCode(categoryCode: String): List<Post> {
        TODO("검색쿼리 ...") // To change body of created functions use File | Settings | File Templates.
    }
}
