package com.ryulth.woom.domain

import com.ryulth.woom.domain.model.AppleUser
import com.ryulth.woom.domain.repository.AppleUserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AppleUserService(
    private val appleUserRepository: AppleUserRepository
) {
    fun save(appleUser: AppleUser): AppleUser {
        return appleUserRepository.save(appleUser)
    }

    fun findByAppleId(appleId: String): AppleUser {
        return appleUserRepository.findByIdOrNull(appleId) ?: throw IllegalArgumentException()
    }

    fun existsByAppleId(appleId: String): Boolean {
        return appleUserRepository.existsById(appleId)
    }
}
