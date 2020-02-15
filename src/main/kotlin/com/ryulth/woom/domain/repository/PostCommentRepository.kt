package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.PostComment
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface PostCommentRepository : CrudRepository<PostComment, String> {
    fun findAllByPostId(postId: String): List<PostComment>
}
