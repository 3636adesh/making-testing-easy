package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PersonServiceParameterizedTests {

    PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository repo = new PersonRepository();
        personService = new PersonService(repo);
    }

    static Stream<Arguments> personPropsProvider() {
        return Stream.of(
                arguments("Siva", "siva@gmail.com"),
                arguments("Prasad", "prasad@gmail.com")
        );
    }

    @ParameterizedTest
    @MethodSource("personPropsProvider")
    void personProps(String name, String email) {
        Person person = personService.create(new Person(null, name, email));
        assertNotNull(person.getId());
        assertEquals(name, person.getName());
        assertEquals(email, person.getEmail());
    }

}
