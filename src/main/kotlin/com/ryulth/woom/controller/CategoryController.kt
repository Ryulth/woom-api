package com.ryulth.woom.controller

import com.ryulth.woom.dto.CategoryInfo
import com.ryulth.woom.service.CategoryInfoService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/category")
class CategoryController(
    private val categoryInfoService: CategoryInfoService
) {
    @ApiOperation(value = "Get all category", notes = "하위 카테고리 까지 가져온다")
    @GetMapping
    fun getCategoryInfo(): ResponseEntity<CategoryInfo> {
        return ResponseEntity.ok(categoryInfoService.getCategoryInfo())
    }
}
