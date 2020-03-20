package com.ryulth.woom.service

import com.ryulth.woom.domain.model.User
import com.ryulth.woom.domain.service.UserService
import com.ryulth.woom.dto.UserSession
import kotlin.concurrent.getOrSet
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserSessionService(
    private val userService: UserService
) {
    companion object {
        private val userThreadLocal = ThreadLocal<User>()
    }

    fun getCurrentUser(): User {
        return userThreadLocal.getOrSet { userService.findByUserId(getCurrentUserSession().userId) }
    }

    fun getCurrentUserSession(): UserSession {
        return SecurityContextHolder.getContext().authentication as UserSession
    }

    fun removeSession() {
        userThreadLocal.remove()
        SecurityContextHolder.clearContext()
    }
}
