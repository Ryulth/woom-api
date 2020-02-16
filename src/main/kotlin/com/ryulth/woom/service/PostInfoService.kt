package com.ryulth.woom.service

import com.ryulth.woom.domain.CategoryService
import com.ryulth.woom.domain.PostCommentService
import com.ryulth.woom.domain.PostService
import com.ryulth.woom.domain.model.Post
import com.ryulth.woom.domain.model.PostComment
import com.ryulth.woom.dto.PostCommentCreateRequest
import com.ryulth.woom.dto.PostCommentInfo
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
    private val postCommentService: PostCommentService,
    private val userSessionService: UserSessionService
) {
    companion object : KLogging() {
        fun postToPostInfo(post: Post, postCommentInfos: List<PostCommentInfo>): PostInfo = PostInfo(
            postId = post.id!!,
            authorId = post.authorId,
            title = post.title,
            content = post.content,
            hasCategorySet = post.hasCategorySet,
            wantCategorySet = post.wantCategorySet,
            location = post.location,
            createdAt = post.createdAt,
            postCommentInfos = postCommentInfos
        )

        fun postCommentToPostCommentInfo(postComment: PostComment): PostCommentInfo = PostCommentInfo(
            postCommentId = postComment.id!!,
            postId = postComment.postId,
            authorId = postComment.authorId,
            content = postComment.content,
            createdAt = postComment.createdAt
        )
    }

    // TODO paging
    fun getPosts(postCategory: String?): PostInfos {
        val posts = postService.findAll()
        val postInfos = mutableListOf<PostInfo>()

        posts.forEach {
            postInfos.add(this.getPostById(it.id!!))
        }

        return PostInfos(postInfos)
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

        return postToPostInfo(post, mutableListOf())
    }

    fun getPostById(postId: String): PostInfo {
        val post = postService.findByPostId(postId)
        val postCommentInfos =
            postCommentService.findAllByPostId(postId).map { postCommentToPostCommentInfo(it) }.toMutableList()
        return postToPostInfo(post, postCommentInfos)
    }

    fun createComment(postId: String, postCommentCreateRequest: PostCommentCreateRequest): PostCommentInfo {
        val authorId = userSessionService.getCurrentUserSession().userId
        val postComment = postCommentService.save(
            PostComment(
                postId = postId,
                authorId = authorId,
                content = postCommentCreateRequest.content
            )
        )

        return postCommentToPostCommentInfo(postComment)
    }
}
