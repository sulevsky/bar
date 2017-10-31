package com.example.bar.company

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.example.bar.company.TokenHelper.token
import static com.example.bar.util.RestDocHelper.array
import static com.example.bar.util.RestDocHelper.toFieldDescriptor
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.springframework.http.HttpHeaders.AUTHORIZATION
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets",
        uriScheme = "https",
        uriHost = "bar.com",
        uriPort = 443)
class CompanyControllerTest extends Specification {
    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper mapper
    @Autowired
    CompanyRepository companyRepository

    List<FieldDescriptor> requestFields = [
            name : "Company name",
            owner: "Company owner"
    ].collect { toFieldDescriptor(it) }


    List<FieldDescriptor> responseFields = [
            id: "Company ID"
    ].collect { toFieldDescriptor(it) } + requestFields

    def cleanup() {
        companyRepository.deleteAll()
    }

    def "create company"() {
        given:
        def body = mapper.writeValueAsString([name: "honda", owner: "suichiro"])

        expect:

        mockMvc.perform(post("/companies")
                                .header(AUTHORIZATION, token("create:company"))
                                .contentType(APPLICATION_JSON)
                                .content(body))
               .andExpect(status().isCreated())
               .andDo(document("company/create",
                               requestFields(requestFields)))

        and: "single company is created"
        def all = companyRepository.findAll()
        all.size() == 1

        and: "has correct property values"
        def company = all[0]
        company.id != null
        company.name == "honda"
        company.owner == "suichiro"

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
               .andDo(document("company/read-by-id",
                               pathParameters(parameterWithName("id").description("Company ID")),
                               responseFields(responseFields)))
    }

    def "read all companies"() {
        given:
        companyRepository.save(new Company(name: "honda", owner: "suichiro"))
        companyRepository.save(new Company(name: "suzuki", owner: "michio"))
        expect:
        mockMvc.perform(get("/companies")
                                .header(AUTHORIZATION, token("read:companyList"))
                                .accept(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath('$', hasSize(2)))
               .andExpect(jsonPath('$[0].name', is("honda")))
               .andDo(document("company/read-all",
                               responseFields(array(responseFields, "Companies list"))))
    }

}
