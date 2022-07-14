package ru.Inside_test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.Inside_test.domain.Person;

import java.util.List;

@Repository
public interface PersonRepository
        extends CrudRepository<Person, Integer> {

    Person findByName(String name);

    Person save(Person person);

    List<Person> findAll();

}