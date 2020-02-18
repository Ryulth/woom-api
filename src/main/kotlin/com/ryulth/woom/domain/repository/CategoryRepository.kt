package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, String> {
    @Modifying
    @Query(value = "UPDATE Category c set c.userCount = c.userCount + 1 WHERE c.categoryCode = ?1")
    fun plusUserCountByCategoryCode(categoryCode: String)
    @Modifying
    @Query(value = "UPDATE Category c set c.postCount = c.postCount + 1 WHERE c.categoryCode = ?1")
    fun plusPostCountByCategoryCode(categoryCode: String)
    @Modifying
    @Query(value = "UPDATE Category c set c.userCount = c.userCount - 1 WHERE c.categoryCode = ?1")
    fun minusUserCountByCategoryCode(categoryCode: String)
    @Modifying
    @Query(value = "UPDATE Category c set c.postCount = c.postCount - 1 WHERE c.categoryCode = ?1")
    fun minusPostCountByCategoryCode(categoryCode: String)
}
