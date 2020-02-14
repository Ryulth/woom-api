package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, String>