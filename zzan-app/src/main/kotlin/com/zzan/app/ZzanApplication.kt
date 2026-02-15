package com.zzan.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@EnableCaching
@EnableJpaAuditing
@EntityScan(basePackages = ["com.zzan"])
@EnableJpaRepositories(basePackages = ["com.zzan"])
@SpringBootApplication(scanBasePackages = ["com.zzan"])
class ZzanApplication

fun main(args: Array<String>) {
    runApplication<ZzanApplication>(*args)
}
