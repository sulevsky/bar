package com.example.bar

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BarApplication{
    @Bean
    fun init(entRepository: EntRepository): ApplicationRunner {
        return ApplicationRunner { entRepository.insert(Ent(1, "asd")).subscribe() }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(BarApplication::class.java, *args)
}

