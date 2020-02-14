package com.ryulth.woom.controller

import com.ryulth.woom.domain.PostService
import com.ryulth.woom.domain.model.Post
import com.ryulth.woom.dto.CategoryInfo
import com.ryulth.woom.dto.PostCreateRequest
import com.ryulth.woom.dto.PostInfo
import com.ryulth.woom.service.PostInfoService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PostController(
    private val postService: PostService,
    private val postInfoService: PostInfoService
) {

    @ApiOperation(value = "Get all post", notes = "dynamodb test ")
    @GetMapping("posts")
    fun getPosts(): ResponseEntity<List<Post>> {
        return ResponseEntity.ok(postService.findAll())
    }

    @ApiOperation(value = "create post", notes = "dynamodb create ")
    @Transactional
    @PostMapping("/post")
    fun createPost(@RequestBody postCreateRequest: PostCreateRequest): ResponseEntity<PostInfo> {
        return ResponseEntity.ok(postInfoService.createPost(postCreateRequest))
    }
}