package com.ryulth.woom.domain.service

import com.ryulth.woom.domain.model.Post
import com.ryulth.woom.domain.repository.dynamodb.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun findAll() = postRepository.findAll().toList()

    fun findByPostId(postId: String) = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException()

    fun save(post: Post): Post = postRepository.save(post)
}
