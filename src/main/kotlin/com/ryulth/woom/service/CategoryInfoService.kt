package com.ryulth.woom.service

import com.ryulth.woom.domain.CategoryService
import com.ryulth.woom.domain.model.Category
import com.ryulth.woom.dto.CategoryInfo
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class CategoryInfoService(
    private val categoryService: CategoryService
) {
    companion object : KLogging() {
        private val categoryCodeSet = mutableSetOf<String>()
        private var categoryInfo: CategoryInfo = CategoryInfo(null, null)
    }

    @Scheduled(fixedRate = 300000)
    fun scheduledCategoryInfo() {
        logger.info { "scheduledCategoryInfo" }
        updateCategoryInfo()
    }

    private fun updateCategoryInfo() {
        val categoryInfoMap = hashMapOf<String, CategoryInfo>()
        val categories: List<Category> = categoryService.findAll()
        categories.forEach { category ->
            categoryCodeSet.add(category.categoryCode)
            category.upperCategoryCode?.let {
                val categoryInfo = categoryInfoMap.getOrPut(category.upperCategoryCode,
                    {
                        CategoryInfo(
                            categoryCode = category.upperCategoryCode,
                            categoryInfos = mutableSetOf()
                        )
                    })
                categoryInfo.categoryInfos?.add(
                    CategoryInfo(
                        categoryCode = category.categoryCode,
                        categoryInfos = null
                    )
                )
            } ?: run {
                categoryInfoMap.putIfAbsent(
                    category.categoryCode,
                    CategoryInfo(
                        categoryCode = category.categoryCode,
                        categoryInfos = mutableSetOf()
                    )
                )
            }
        }
        categoryInfo = CategoryInfo(
            categoryCode = null,
            categoryInfos = categoryInfoMap.values.toMutableSet()
        )
    }

    fun getCategoryInfo() = categoryInfo

    fun checkCategoryCode(categoryCode: String) = categoryCodeSet.contains(categoryCode)
}