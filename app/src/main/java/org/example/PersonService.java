package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person with ID " + id + " not found"));
    }

    public List<Person> getPersonsByColor(Color color) {
        return personRepository.findByColor(color);
    }

    public List<Color> getColors() {
        return Arrays.asList(Color.values());
    }

    public ResponseEntity<Person> addPerson(Person person) {
        log.info("added Person: " + person.toString());
        personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }
}