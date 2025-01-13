package com.example.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository repo;

    @InjectMocks
    PersonService service;


    private static final String EMAIL = "3636adesh@foo.com";
    private static final String PASSWORD = "password";

    static Person person() {
        return new Person(1L, "Siva", EMAIL, PASSWORD);
    }

    @Test
    void should_LoginSuccessfully() {
        Person person = person();
        when(repo.findByEmailAndPassword(eq(EMAIL), anyString()))
                .thenReturn(Optional.of(person));
        var token = service.login(EMAIL, PASSWORD);
        assertThat(token).isNotNull();
    }

    @Test
    void loginFailure() {
        when(repo.findByEmailAndPassword(EMAIL, PASSWORD))
                .thenReturn(Optional.empty());

        String token = service.login(EMAIL, PASSWORD);

        assertThat(token).isNull();
    }

    @Test
    void findByEmail() {
        var person = person();
        when(repo.findByEmail(eq(EMAIL))).thenReturn(Optional.of(person));
        Optional<Person> existPerson = service.findByEmail(EMAIL);
        assertThat(existPerson).isPresent();
        assertThat(existPerson.get().getEmail()).isEqualTo(EMAIL);
        assertThat(existPerson.get().getPassword()).isEqualTo(PASSWORD);

    }


    @Test
    void shouldCreatePersonSuccessfully() {
        when(repo.findByEmail("siva@gmail.com")).thenReturn(Optional.empty());
        when(repo.create(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Person person = service.create("Siva", "SIVA@gmail.com", "siva123");

        assertEquals("Siva", person.getName());
        assertEquals("siva@gmail.com", person.getEmail());

        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(repo).create(argumentCaptor.capture());
        assertEquals(person, argumentCaptor.getValue());

        Person value = argumentCaptor.getValue();
        assertEquals("Siva", value.getName());
        assertEquals("siva@gmail.com", value.getEmail());

    }

    @Test
    void updatePerson() {
        Person person = new Person(1L, "Siva", "siva@gmail.com", "siva123");

        doNothing().when(repo).update(any(Person.class));
        //doThrow(new RuntimeException("Invalid email")).when(repo).update(any(Person.class));

        service.update(person);

        verify(repo).update(any(Person.class));
        //verify(repo, times(1)).update(any(Person.class));
        //verify(repo, atMostOnce()).update(any(Person.class));
        //verify(repo, atLeastOnce()).update(any(Person.class));
    }

}
