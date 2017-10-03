package com.example.bar

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EntRepository : ReactiveMongoRepository<Ent, Int>

data class Ent(@Id var idField:Int = 0, var valueField:String = "")