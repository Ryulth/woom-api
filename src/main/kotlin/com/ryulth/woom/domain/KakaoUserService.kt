package com.ryulth.woom.domain

import com.ryulth.woom.domain.model.KakaoUser
import com.ryulth.woom.domain.repository.KakaoUserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class KakaoUserService(
    private val kakaoUserRepository: KakaoUserRepository
) {
    fun save(kakaoUser: KakaoUser): KakaoUser {
        return kakaoUserRepository.save(kakaoUser)
    }

    fun findByKakaoId(kakaoId: String): KakaoUser {
        return kakaoUserRepository.findByIdOrNull(kakaoId) ?: throw IllegalArgumentException()
    }

    fun existsByKakaoId(kakaoId: String): Boolean {
        return kakaoUserRepository.existsById(kakaoId)
    }
}
