package com.ryulth.woom.domain.service

import com.ryulth.woom.domain.model.Post
import com.ryulth.woom.domain.repository.dynamodb.PostRepository
import com.ryulth.woom.domain.repository.elasticsearch.PostSearchRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val postSearchRepository: PostSearchRepository
) {
    fun findAll(): List<Post> = postSearchRepository.findAll()

    fun findByPostId(postId: String): Post = postRepository.findByIdOrNull(postId) ?: throw IllegalArgumentException()

    fun findAllByWantCategory(category: String): List<Post> =
            postSearchRepository.findAllByWantCategorySetContain(category)

    fun findAllByHasCategory(category: String): List<Post> =
            postSearchRepository.findAllByHasCategorySetContain(category)

    fun save(post: Post): Post = postRepository.save(post)
}
