package com.bar.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController implements ApplicationRunner {

    private TestRepository testRepository;

    public TestController(@Autowired TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping
    public String hello() {
        return "Hellooooo";
    }

    @GetMapping("/ent")
    public List<Ent> ent() {
        return testRepository.findAll();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Ent ent = new Ent("hello", 100);
        testRepository.save(ent);
    }
}
