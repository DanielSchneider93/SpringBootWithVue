package org.example;

import java.util.List;

public interface PersonInterface {
    List<Person> getAllPersons();

    Person getPersonById(Long id);

    List<Person> getPersonsByColor(Color color);
}