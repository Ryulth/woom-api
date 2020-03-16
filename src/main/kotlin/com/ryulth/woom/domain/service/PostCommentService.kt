package com.ryulth.woom.domain.service

import com.ryulth.woom.domain.model.PostComment
import com.ryulth.woom.domain.repository.dynamodb.PostCommentRepository
import org.springframework.stereotype.Service

@Service
class PostCommentService(
    private val postCommentRepository: PostCommentRepository
) {
    fun findAll() = postCommentRepository.findAll().toList()

    fun save(postComment: PostComment): PostComment = postCommentRepository.save(postComment)

    fun findAllByPostId(postId: String) = postCommentRepository.findAllByPostId(postId)
}
