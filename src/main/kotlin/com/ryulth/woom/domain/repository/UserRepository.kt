package com.ryulth.woom.domain.repository

import com.ryulth.woom.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT count(*) FROM User u where u.id in :ids")
    fun countByIds(@Param("ids") ids: Set<Long>): Long
}
