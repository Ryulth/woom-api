package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.KakaoUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KakaoUserRepository : JpaRepository<KakaoUser, String>
