package com.ryulth.woom.service

import com.ryulth.woom.domain.model.Category
import com.ryulth.woom.domain.service.CategoryService
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
                val categoryInfo = categoryInfoMap.getOrPut(
                    category.upperCategoryCode,
                    {
                        CategoryInfo(
                            categoryCode = category.upperCategoryCode,
                            categoryInfos = mutableSetOf(),
                            userCount = 0,
                            postCount = 0
                        )
                    })
                categoryInfo.categoryInfos?.add(
                    CategoryInfo(
                        categoryCode = category.categoryCode,
                        userCount = category.userCount,
                        postCount = category.postCount
                    )
                )
            } ?: run {
                val categoryInfo = categoryInfoMap.getOrPut(
                    category.categoryCode,
                    {
                        CategoryInfo(
                            categoryCode = category.categoryCode,
                            categoryInfos = mutableSetOf(),
                            userCount = category.userCount,
                            postCount = category.postCount
                        )
                    }
                )
                categoryInfo.userCount = category.userCount
                categoryInfo.postCount = category.postCount
            }
        }
        categoryInfo = CategoryInfo(
            categoryInfos = categoryInfoMap.values.toMutableSet()
        )
    }

    fun getCategoryInfo() = categoryInfo

    fun checkCategoryCode(categoryCode: String) = categoryCodeSet.contains(categoryCode)

    fun filterCategoryCodeSet(inputCategoryCodeSet: Set<String>) =
        inputCategoryCodeSet.filter { this.checkCategoryCode(it) }.toMutableSet()
}
