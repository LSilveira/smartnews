package com.lsilveira.smartnews

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SmartNewsApplication

fun main(args: Array<String>) {
	runApplication<SmartNewsApplication>(*args)
}
