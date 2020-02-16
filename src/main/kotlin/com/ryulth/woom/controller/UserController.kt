package com.ryulth.woom.controller

import com.ryulth.woom.dto.UserInfo
import com.ryulth.woom.service.UserInfoService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userInfoService: UserInfoService
) {
    @ApiOperation(value = "Get profile", notes = "userId params 없으면 자기 자신 프로필이")
    @GetMapping("/profile")
    fun getCategoryInfo(@RequestParam("userId", required = false)userId: Long?): ResponseEntity<UserInfo> {
        return ResponseEntity.ok(userInfoService.getUserProfile(userId))
    }
}
