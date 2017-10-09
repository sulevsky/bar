package com.example.bar.company

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.http.ResponseEntity
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
class CompanyHandler(val companyRepository: CompanyRepository) {

    @PostMapping
    fun create(@RequestBody company: Company): ResponseEntity<Void> {
        companyRepository.insert(company)
        return ResponseEntity.created(URI.create("/companies/${company.id}")).build()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String) = companyRepository.findOne(id)

    @GetMapping
    fun getAll() = companyRepository.findAll()
}