package com.ryulth.woom.service

import com.ryulth.woom.domain.model.Post
import com.ryulth.woom.domain.service.CategoryService
import com.ryulth.woom.domain.service.PostService
import com.ryulth.woom.dto.PostCreateRequest
import com.ryulth.woom.dto.PostInfo
import com.ryulth.woom.dto.PostInfos
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class PostInfoService(
    private val postService: PostService,
    private val categoryInfoService: CategoryInfoService,
    private val categoryService: CategoryService,
    private val userSessionService: UserSessionService
) {
    companion object : KLogging() {
        fun postToPostInfo(post: Post): PostInfo = PostInfo(
            postId = post.id!!,
            authorId = post.authorId,
            title = post.title,
            content = post.content,
            hasCategorySet = post.hasCategorySet ?: mutableSetOf(),
            wantCategorySet = post.wantCategorySet ?: mutableSetOf(),
            location = post.location,
            createdAt = post.createdAt
        )
    }

    // TODO paging
    fun getPosts(categoryCode: String?): PostInfos {
        val isWoomCategory = categoryCode?.let { categoryInfoService.checkCategoryCode(it) } ?: false
        return if (isWoomCategory) {
            PostInfos(postService.findAllByHasCategory(categoryCode!!).map { postToPostInfo(it) }.toList())
        } else {
            PostInfos(postService.findAll().map { postToPostInfo(it) }.toList())
        }
    }

    fun createPost(postCreateRequest: PostCreateRequest): PostInfo {
        val authorId = userSessionService.getCurrentUserSession().userId
        val filteredMyCategorySet = categoryInfoService.filterCategoryCodeSet(postCreateRequest.hasCategorySet)
        val filteredWantCategorySet = categoryInfoService.filterCategoryCodeSet(postCreateRequest.wantCategorySet)

        val post = postService.save(
            Post(
                authorId = authorId,
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

    fun getPostById(postId: String): PostInfo =
        postToPostInfo(postService.findByPostId(postId))
}
