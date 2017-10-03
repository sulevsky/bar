package com.example.bar

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class EntRepositoryTest extends Specification {
    @Autowired
    EntRepository entRepository

    def "db works"() {
        when:
        entRepository.insert(new Ent(1, "asd")).subscribe()
        then:
        def all = entRepository.findAll()

        all.blockFirst() == new Ent(1, "asd")

    }
}
