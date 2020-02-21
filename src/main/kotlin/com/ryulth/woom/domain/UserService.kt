package com.ryulth.woom.domain

import com.ryulth.woom.domain.model.User
import com.ryulth.woom.domain.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun saveAll(users: List<User>) {
        userRepository.saveAll(users)
    }

    fun findByUserId(userId: Long): User {
        return userRepository.findByIdOrNull(userId) ?: throw IllegalArgumentException()
    }

    fun countByUserIds(userIds: Set<Long>): Long {
        return userRepository.countByIds(userIds)
    }
}
