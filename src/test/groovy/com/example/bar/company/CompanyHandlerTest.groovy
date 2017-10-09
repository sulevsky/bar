package com.example.bar.company

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CompanyHandlerTest extends Specification {
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper mapper
    @Autowired
    CompanyRepository companyRepository

    def cleanup() {
        companyRepository.deleteAll()
    }

    def "create company"() {
        given:
        def body = mapper.writeValueAsString(new Company(name: "honda", owner: "suichiro"))

        expect:

        mockMvc.perform(post("/companies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
               .andExpect(status().isCreated())

        companyRepository.findAll().size() == 1
    }

    def "read company"() {
        given:
        def companyId = companyRepository.save(new Company(name: "honda", owner: "suichiro")).id

        expect:

        mockMvc.perform(get("/companies/{id}", companyId)
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath('$.name', is("honda")))
               .andExpect(jsonPath('$.owner', is("suichiro")))
    }

    def "read all companies"() {
        given:
        companyRepository.save(new Company(name: "honda", owner: "suichiro"))

        expect:
        mockMvc.perform(get("/companies")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath('$', hasSize(1)))
               .andExpect(jsonPath('$[0].name', is("honda")))
    }

}
