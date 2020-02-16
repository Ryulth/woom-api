package com.ryulth.woom.controller

import com.ryulth.woom.dto.UserAboutMe
import com.ryulth.woom.dto.UserCategorySet
import com.ryulth.woom.dto.UserImageInfo
import com.ryulth.woom.dto.UserInfo
import com.ryulth.woom.service.UserInfoService
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
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
    fun getCategoryInfo(@RequestParam("userId", required = false) userId: Long?): ResponseEntity<UserInfo> {
        return ResponseEntity.ok(userInfoService.getUserProfile(userId))
    }

    @ApiOperation(
        value = "Update categorySet",
        notes = "유저의 카테고리 셋을 업데이트 하는데 한번에 두개 다 할 수 있고 NotNullOrEmpty 인 필드만 업데이트 한다 "
    )
    @PutMapping("/profile/categorySet")
    fun updateCategorySet(@RequestBody userCategorySet: UserCategorySet): ResponseEntity<UserCategorySet> {
        return ResponseEntity.ok(userInfoService.updateInterestedCategorySet(userCategorySet))
    }

    @ApiOperation(value = "Update aboutMe", notes = "유저 자기소개 변걍")
    @PutMapping("/profile/aboutMe")
    fun updateAboutMe(@RequestBody userAboutMe: UserAboutMe): ResponseEntity<UserAboutMe> {
        return ResponseEntity.ok(userInfoService.updateAboutMe(userAboutMe))
    }

    @ApiOperation(value = "Update user image", notes = "유저 자기소개 변걍")
    @PutMapping("/profile/images")
    fun updateAboutMe(@RequestBody userImageInfo: UserImageInfo): ResponseEntity<UserImageInfo> {
        return ResponseEntity.ok(userInfoService.updateUserImage(userImageInfo))
    }
}
