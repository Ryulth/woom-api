package com.ryulth.woom.domain.service

import com.ryulth.woom.domain.model.EmailUser
import com.ryulth.woom.domain.repository.jpa.EmailUserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EmailUserService(
    private val emailUserRepository: EmailUserRepository
) {
    fun save(emailUser: EmailUser): EmailUser {
        return emailUserRepository.save(emailUser)
    }

    fun findByEmail(email: String): EmailUser {
        return emailUserRepository.findByIdOrNull(email) ?: throw IllegalArgumentException()
    }

    fun existsByEmail(email: String): Boolean {
        return emailUserRepository.existsById(email)
    }
}
