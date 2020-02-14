package com.ryulth.woom.domain

import com.ryulth.woom.domain.model.Category
import com.ryulth.woom.domain.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService (
    private val categoryRepository: CategoryRepository
) {
    fun findAll(): List<Category> = categoryRepository.findAll()

    fun plusUserCount(categoryCode: String) {
        categoryRepository.plusUserCountByCategoryCode(categoryCode)
    }

    fun plusPostCount(categoryCode: String) {
        categoryRepository.plusPostCountByCategoryCode(categoryCode)
    }
}