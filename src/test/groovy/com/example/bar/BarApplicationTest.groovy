package com.example.bar

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class BarApplicationTest extends Specification {
    def "context loads"(){
        expect:
        true
    }
}
