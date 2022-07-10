package com.example.people_managment_system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostgreSqlApplication

fun main(args: Array<String>) {
	val runApplication = runApplication<PostgreSqlApplication>(*args)
}

