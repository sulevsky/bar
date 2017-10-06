package com.example.bar

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BarApplication

fun main(args: Array<String>) {
    SpringApplication.run(BarApplication::class.java, *args)
}

