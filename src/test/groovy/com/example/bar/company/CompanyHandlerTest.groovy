package com.example.bar.company

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.example.bar.company.TokenHelper.token
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.MediaType.APPLICATION_JSON
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
                                .header(AUTHORIZATION, token("create:company"))
                                .contentType(APPLICATION_JSON)
                                .content(body))
               .andExpect(status().isCreated())

        companyRepository.findAll().size() == 1
    }

    def "cannot access with invalid token"() {
        given:
        def body = mapper.writeValueAsString(new Company(name: "honda", owner: "suichiro"))

        expect:

        mockMvc.perform(post("/companies")
                                .header(AUTHORIZATION, token("INVALID_SCOPE"))
                                .contentType(APPLICATION_JSON)
                                .content(body))
               .andExpect(status().isForbidden())
    }

    def "read company"() {
        given:
        def companyId = companyRepository.save(new Company(name: "honda", owner: "suichiro")).id

        expect:

        mockMvc.perform(get("/companies/{id}", companyId)
                                .header(AUTHORIZATION, token("read:company"))
                                .accept(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath('$.name', is("honda")))
               .andExpect(jsonPath('$.owner', is("suichiro")))
    }

    def "read all companies"() {
        given:
        companyRepository.save(new Company(name: "honda", owner: "suichiro"))

        expect:
        mockMvc.perform(get("/companies")
                                .header(AUTHORIZATION, token("read:companyList"))
                                .accept(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath('$', hasSize(1)))
               .andExpect(jsonPath('$[0].name', is("honda")))
    }

}
