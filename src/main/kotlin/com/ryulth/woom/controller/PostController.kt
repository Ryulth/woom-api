package com.ryulth.woom.controller

import com.ryulth.woom.dto.PostCommentCreateRequest
import com.ryulth.woom.dto.PostCommentInfo
import com.ryulth.woom.dto.PostCreateRequest
import com.ryulth.woom.dto.PostInfo
import com.ryulth.woom.dto.PostInfos
import com.ryulth.woom.service.PostInfoService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postInfoService: PostInfoService
) {
    @ApiOperation(value = "Get post ", notes = "category code 가 없으면 전체 검 ")
    @GetMapping
    fun getPosts2(@RequestParam("categoryCode", required = false)categoryCode: String?): ResponseEntity<PostInfos> {
        return ResponseEntity.ok(postInfoService.getPosts(categoryCode))
    }

    @ApiOperation(value = "create post", notes = "create ")
    @Transactional
    @PostMapping
    fun createPost(@RequestBody postCreateRequest: PostCreateRequest): ResponseEntity<PostInfo> {
        return ResponseEntity.ok(postInfoService.createPost(postCreateRequest))
    }

    @ApiOperation(value = "get post by id", notes = "post id 로 가져옴 ")
    @GetMapping("/{postId}")
    fun getPostById(@PathVariable("postId") postId: String): ResponseEntity<PostInfo> {
        return ResponseEntity.ok(postInfoService.getPostById(postId))
    }

    @ApiOperation(value = "create comment", notes = "댓글 달기 ")
    @Transactional
    @PostMapping("/{postId}/comment")
    fun createComment(
        @PathVariable("postId") postId: String,
        @RequestBody postCommentCreateRequest: PostCommentCreateRequest
    ): ResponseEntity<PostCommentInfo> {
        return ResponseEntity.ok(postInfoService.createComment(postId, postCommentCreateRequest))
    }
}
