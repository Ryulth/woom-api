package com.ryulth.woom.service

import com.ryulth.woom.domain.model.User
import com.ryulth.woom.domain.model.UserImage
import com.ryulth.woom.domain.service.CategoryService
import com.ryulth.woom.domain.service.UserService
import com.ryulth.woom.dto.UserAboutMe
import com.ryulth.woom.dto.UserCategorySet
import com.ryulth.woom.dto.UserImageInfo
import com.ryulth.woom.dto.UserInfo
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class UserInfoService(
    private val userService: UserService,
    private val userSessionService: UserSessionService,
    private val categoryInfoService: CategoryInfoService,
    private val categoryService: CategoryService
) {
    companion object : KLogging() {
        fun userToUserInfo(user: User): UserInfo = UserInfo(
            userId = user.id!!,
            woomId = user.woomId,
            loginType = user.loginType,
            firstName = user.userName.firstName,
            lastName = user.userName.lastName,
            nickName = user.userName.nickName,
            aboutMe = user.aboutMe,
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

    fun updateInterestedCategorySet(userCategorySet: UserCategorySet): UserCategorySet {
        val user = userSessionService.getCurrentUser()
        val hasCategorySetRequest = userCategorySet.hasCategorySet
        if (!hasCategorySetRequest.isNullOrEmpty()) {
            val filteredCategorySet = categoryInfoService.filterCategoryCodeSet(hasCategorySetRequest)
            user.hasCategorySet.forEach { categoryService.minusUserCount(it) }
            filteredCategorySet.forEach { categoryService.plusUserCount(it) }

            user.hasCategorySet = filteredCategorySet
        }
        val interestedCategorySetRequest = userCategorySet.interestedCategorySet
        if (!interestedCategorySetRequest.isNullOrEmpty()) {
            val filteredCategorySet = categoryInfoService.filterCategoryCodeSet(interestedCategorySetRequest)
            user.interestedCategorySet = filteredCategorySet
        }
        userService.save(user)
        return UserCategorySet(
            interestedCategorySet = user.interestedCategorySet,
            hasCategorySet = user.hasCategorySet
        )
    }

    fun updateAboutMe(userAboutMe: UserAboutMe): UserAboutMe {
        val user = userSessionService.getCurrentUser()
        user.aboutMe = userAboutMe.aboutMe
        userService.save(user)
        return UserAboutMe(aboutMe = user.aboutMe)
    }

    fun updateUserImage(userImageInfo: UserImageInfo): UserImageInfo {
        val user = userSessionService.getCurrentUser()
        user.userImage = UserImage(
            thumbnailImageUrl = userImageInfo.thumbnailImageUrl,
            profileImageUrl = userImageInfo.profileImageUrl
        )
        userService.save(user)
        return UserImageInfo(
            thumbnailImageUrl = user.userImage.thumbnailImageUrl,
            profileImageUrl = user.userImage.profileImageUrl
        )
    }
}
