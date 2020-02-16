package com.ryulth.woom.domain.model

import com.ryulth.woom.dto.LoginType
import com.ryulth.woom.util.StringSetConverter
import javax.persistence.Convert
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Lob

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    val woomId: String,

    @Embedded
    val userName: UserName,

    @Embedded
    val userImage: UserImage,

    val publicEmail: String? = null,

    @Enumerated(STRING)
    val loginType: LoginType,

    @Lob
    @Convert(converter = StringSetConverter::class)
    val interestedCategorySet: Set<String>,

    @Lob
    @Convert(converter = StringSetConverter::class)
    val hasCategorySet: Set<String>
)
