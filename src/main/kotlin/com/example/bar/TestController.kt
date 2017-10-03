package com.example.bar

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/")
class TestController(val entRepository: EntRepository) {

    @GetMapping
    fun hello(): Mono<String> {
        return Mono.just("Hellooooo")
    }

    @GetMapping("/ent")
    fun ent(): Flux<Ent> {
        return entRepository.findAll()
    }
}