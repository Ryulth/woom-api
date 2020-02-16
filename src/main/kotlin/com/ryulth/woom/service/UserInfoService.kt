package com.ryulth.woom.service

import com.ryulth.woom.domain.UserService
import com.ryulth.woom.domain.model.User
import com.ryulth.woom.dto.UserInfo
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class UserInfoService(
    private val userService: UserService,
    private val userSessionService: UserSessionService
) {
    companion object : KLogging() {
        fun userToUserInfo(user: User): UserInfo = UserInfo(
            woomId = user.woomId,
            loginType = user.loginType,
            firstName = user.userName.firstName,
            lastName = user.userName.lastName,
            nickName = user.userName.nickName,
            interestedCategorySet = user.interestedCategorySet,
            hasCategorySet = user.hasCategorySet,
            thumbnailImageUrl = user.userImage.thumbnailImageUrl,
            profileImageUrl = user.userImage.profileImageUrl
        )
    }

    fun getUserProfile(userId: Long?): UserInfo =
        userToUserInfo(userId?.let { userService.findByUserId(it) }
            ?: run {
                userSessionService.getCurrentUser()
            }
        )
}
