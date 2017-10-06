package com.example.bar.company

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification

@SpringBootTest

class CompanyHandlerTest extends Specification {
    WebTestClient client

    @Autowired
    ObjectMapper mapper

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    CompanyRepository companyRepository

    def setup() {
        client = WebTestClient.bindToApplicationContext(applicationContext).build()
    }

    def cleanup() {
        companyRepository.deleteAll().block()
    }

    def "create company"() {
        given:
        Mono<String> body = Mono.just(new Company(name: "honda",
                                                  owner: "suichiro"))
                                .map { mapper.writeValueAsString(it) }

        expect:
        client.post().uri("/companies")
              .contentType(MediaType.APPLICATION_JSON)
              .body(body, String.class)
              .exchange()
              .expectStatus().isCreated()

    }

    def "read company"() {
        given:
        def companyId = companyRepository.save(new Company(name: "honda", owner: "suichiro")).block().id

        expect:

        Company company = client.get().uri("/companies/{id}", companyId)
                                .accept(MediaType.APPLICATION_JSON)
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(Company.class)
                                .returnResult()
                                .responseBody

        company.name == "honda"
        company.owner == "suichiro"
    }

    def "read all companies"() {
        given:
        def companyId = companyRepository.save(new Company(name: "honda", owner: "suichiro")).block().id

        expect:
        List<Company> companies = client.get().uri("/companies")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .exchange()
                                        .expectStatus().isOk()
                                        .expectBodyList(Company.class)
                                        .returnResult()
                                        .responseBody
        companies.size() == 1
    }

}
