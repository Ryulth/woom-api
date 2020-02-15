package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.Post

interface PostAdditionRepository {
    fun findAllByCategoryCode(categoryCode: String): List<Post>
}
