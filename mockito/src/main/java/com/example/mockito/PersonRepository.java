package com.example.mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PersonRepository {
    private static final Map<Long, Person> PERSON_DB = new HashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0L);

    public Optional<Person> findByEmailAndPassword(String email, String password) {
        return PERSON_DB.values().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email) && p.getPassword().equals(password))
                .findAny();
    }

    public Person create(Person person) {
        person.setId(ID_GENERATOR.incrementAndGet());
        PERSON_DB.put(person.getId(), person);
        return person;
    }

    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(PERSON_DB.get(id));
    }

    public List<Person> findAll() {
        return new ArrayList<>(PERSON_DB.values());
    }

    public Optional<Person> findByEmail(String email) {
        return PERSON_DB.values().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findAny();
    }

    public void update(Person person) {
        if(!PERSON_DB.containsKey(person.getId())) {
            throw new RuntimeException("Person not found");
        }
        PERSON_DB.put(person.getId(), person);
    }

    public void deleteById(Long id) {
        if(!PERSON_DB.containsKey(id)) {
            throw new RuntimeException("Person not found");
        }
        PERSON_DB.remove(id);
    }

    public void deleteAll() {
        PERSON_DB.clear();
    }
}
