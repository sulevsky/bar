package com.bar.example;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Ent, String> {
}
