package com.ryulth.woom.domain.repository.elasticsearch

import com.ryulth.woom.domain.model.Post

interface PostSearchRepository {
    fun findAll(): List<Post>
    fun findAllByHasCategorySetContain(hasCategory: String): List<Post>
    fun findAllByWantCategorySetContain(hasCategory: String): List<Post>
}
