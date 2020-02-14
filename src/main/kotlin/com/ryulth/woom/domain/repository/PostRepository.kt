package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.Post
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface PostRepository: CrudRepository<Post, String>