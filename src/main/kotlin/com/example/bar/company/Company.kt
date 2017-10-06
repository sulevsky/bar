package com.example.bar.company

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*

data class Company(
        @Id
        var id: String = UUID.randomUUID().toString(),
        var name: String = "",
        var owner: String = ""
)

interface CompanyRepository : ReactiveMongoRepository<Company, String>

@Configuration
class CompanyRouteConfig(val companyHandler: CompanyHandler) {
    @Bean
    fun companyRouter() = org.springframework.web.reactive.function.server.router {
        POST("/companies", companyHandler::create)
        GET("/companies/{id}", companyHandler::getById)
        GET("/companies", companyHandler::getAll)
    }
}

@Component
class CompanyHandler(val companyRepository: CompanyRepository) {

    fun create(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(Company::class.java)
                .flatMap(companyRepository::insert)
                .flatMap { ServerResponse.created(URI.create("/companies/${it.id}")).build() }
    }

    fun getById(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id = serverRequest.pathVariable("id")
        return ServerResponse.ok().body(companyRepository.findById(id), Company::class.java)
    }

    fun getAll(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(companyRepository.findAll(), Company::class.java)
    }
}