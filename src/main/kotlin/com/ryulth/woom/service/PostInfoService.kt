package com.ryulth.woom.service

import com.ryulth.woom.domain.CategoryService
import com.ryulth.woom.domain.PostService
import com.ryulth.woom.domain.model.Post
import com.ryulth.woom.dto.PostCreateRequest
import com.ryulth.woom.dto.PostInfo
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class PostInfoService(
    private val postService: PostService,
    private val categoryInfoService: CategoryInfoService,
    private val categoryService: CategoryService
) {
    companion object : KLogging() {
        fun postToPostInfo(post: Post): PostInfo = PostInfo(
            postId = post.id!!,
            title = post.title,
            content = post.content,
            hasCategorySet = post.hasCategorySet,
            wantCategorySet = post.wantCategorySet,
            location = post.location,
            createdAt = post.createdAt
        )
    }

    fun createPost(postCreateRequest: PostCreateRequest): PostInfo {
        val filteredMyCategorySet = postCreateRequest.hasCategorySet
            .filter { categoryInfoService.checkCategoryCode(it) }
            .toMutableSet()
        val filteredWantCategorySet = postCreateRequest.wantCategorySet
            .filter { categoryInfoService.checkCategoryCode(it) }
            .toMutableSet()

        val post = postService.save(
            Post(
                title = postCreateRequest.title,
                content = postCreateRequest.content,
                hasCategorySet = filteredMyCategorySet.ifEmpty { null },
                wantCategorySet = filteredWantCategorySet.ifEmpty { null },
                location = postCreateRequest.location
            )
        )

        post.hasCategorySet?.forEach { categoryService.plusPostCount(it) }

        return postToPostInfo(post)
    }
}