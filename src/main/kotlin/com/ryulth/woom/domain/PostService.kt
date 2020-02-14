package com.ryulth.woom.domain

import com.ryulth.woom.domain.model.Post
import com.ryulth.woom.domain.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun findAll() = postRepository.findAll().toList()

    fun save(post: Post) = postRepository.save(post)
}