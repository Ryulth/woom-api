package com.ryulth.woom.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.ryulth.woom.domain.repository.jpa"])
class JpaConfig()