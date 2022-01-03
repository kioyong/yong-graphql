package com.yong;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootApplication
public class YongGraphQlApplication {

    public static void main(String[] args) {
        SpringApplication.run(YongGraphQlApplication.class, args);
    }

}

interface GreetingRepository extends ReactiveMongoRepository<Customer, String> {

    Flux<Customer> findByName(String name);
}


@Controller
@RequiredArgsConstructor
class GreetingGraphQlController {

    private final GreetingRepository repository;

    @SchemaMapping(typeName = "Query", field = "customers")
    public Flux<Customer> customers() {
        return repository.findAll();
    }


    @QueryMapping
    public Flux<Customer> customersByName(@Argument String name){
        return repository.findByName( name);
    }

}


@Data
class Customer {

    @Id
    private String id;

    private String name;

    private List<Email> emails;
}


@Data
class Email {
    private String id;
    private String email;
}