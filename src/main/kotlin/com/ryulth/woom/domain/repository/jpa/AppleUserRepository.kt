package com.ryulth.woom.domain.repository.jpa

import com.ryulth.woom.domain.model.AppleUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppleUserRepository : JpaRepository<AppleUser, String>
