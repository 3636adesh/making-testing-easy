package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonServiceTest {

    PersonService personService;

    @BeforeEach
    void setUp() {
        var repository = new PersonRepository();
        personService = new PersonService(repository);

    }

    @AfterEach
    void tearDown() {
        personService.deleteAll();
    }

    @Nested
    class CreatePersonTest {

        @Test
        void shouldCreatePersonSuccessfully() {
            var person = new Person(null, "John", "Doe");
            var createdPerson = personService.create(person);
            assertNotNull(createdPerson.getId());
            assertEquals("John", createdPerson.getName());
            assertEquals("Doe", createdPerson.getEmail());
        }

        @Test
        void shouldThrowExceptionPersonWithExistingEmail() {
            var person = new Person(null, "John", "Doe");
            personService.create(person);
            assertThatThrownBy(() -> personService.create(person))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Person with email '" + person.getEmail() + "' already exists");
        }
    }

    @Nested
    class FindPersonTest {
        String email = "john.doe@gmail.com";

        @BeforeEach
        void setUp() {
            email = UUID.randomUUID() +"@gmail.com";
            personService.create(new Person(null, "Siva", email));
        }
        @Test
        void shouldGetPersonByEmailWhenExists() {
            Optional<Person> optionalPerson = personService.findByEmail(email);
            assertThat(optionalPerson).isPresent();
        }

        @Test
        void shouldGetEmptyWhenPersonByEmailNotExists() {
            Optional<Person> optionalPerson = personService.findByEmail("random@mail.com");
            assertThat(optionalPerson).isEmpty();
        }
    }
}
