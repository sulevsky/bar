package com.example.bar.company

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

data class Company(
        @Id
        var id: String = UUID.randomUUID().toString(),
        var name: String = "",
        var owner: String = ""
)

interface CompanyRepository : MongoRepository<Company, String>

@RestController
@RequestMapping("/companies")
class CompanyController(val companyRepository: CompanyRepository) {

    @PostMapping
    @PreAuthorize("hasAuthority('create:company')")
    fun create(@RequestBody company: Company): ResponseEntity<Void> {
        companyRepository.insert(company)
        return ResponseEntity.created(URI.create("/companies/${company.id}")).build()
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:company')")
    fun getById(@PathVariable id: String) = companyRepository.findOne(id)

    @GetMapping
    @PreAuthorize("hasAuthority('read:companyList')")
    fun getAll() = companyRepository.findAll()
}
