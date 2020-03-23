package com.ryulth.woom.service

import com.ryulth.woom.domain.model.PostComment
import com.ryulth.woom.domain.service.PostCommentService
import com.ryulth.woom.dto.PostCommentCreateRequest
import com.ryulth.woom.dto.PostCommentInfo
import com.ryulth.woom.dto.PostCommentInfos
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class PostCommentInfoService(
    private val postCommentService: PostCommentService,
    private val userSessionService: UserSessionService
) {
    companion object : KLogging() {
        fun postCommentToPostCommentInfo(postComment: PostComment): PostCommentInfo = PostCommentInfo(
            postCommentId = postComment.id,
            postId = postComment.postId,
            authorId = postComment.authorId,
            content = postComment.content,
            createdAt = postComment.createdAt
        )
    }

    fun getAllPostCommentByPostId(postId: String): PostCommentInfos =
        PostCommentInfos(postCommentService.findAllByPostId(postId).map { postCommentToPostCommentInfo(it) }.toList())

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
