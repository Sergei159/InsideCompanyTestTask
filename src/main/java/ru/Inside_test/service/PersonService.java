package ru.Inside_test.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.Inside_test.domain.Person;
import ru.Inside_test.repository.PersonRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements Store {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public ResponseEntity<Person> findById(int id) {
        Optional<Person> person = repository.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    public Person patch(int id, Person person) throws InvocationTargetException, IllegalAccessException {
        return (Person) patch(repository, id, person);
    }

    public Person findByName(String name) {
        return repository.findByName(name);
    }


}
