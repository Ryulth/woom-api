package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.EmailUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailUserRepository : JpaRepository<EmailUser, String>
