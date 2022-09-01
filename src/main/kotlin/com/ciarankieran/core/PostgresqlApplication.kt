package com.ciarankieran.core

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties
@EnableEncryptableProperties
class PostgreSqlApplication

fun main(args: Array<String>) {
	runApplication<PostgreSqlApplication>(*args)}

