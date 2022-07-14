package ru.Inside_test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.Inside_test.domain.Person;
import ru.Inside_test.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;
    private final ObjectMapper objectMapper;

    public PersonController(PersonService personService, BCryptPasswordEncoder encoder, ObjectMapper objectMapper) {
        this.personService = personService;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    /**
     * *создать(зарегистрировать) нового пользователя
     */

    @PostMapping("/sign-up")
    public ResponseEntity savePerson(@Valid @RequestBody Person person) {
        Optional<Person> result = Optional.ofNullable(personService.findByName(person.getName()));
        if (!result.isPresent()) {
            person.setPassword(encoder.encode(person.getPassword()));
            personService.save(person);
            return new ResponseEntity<>(
                    person,
                    HttpStatus.CREATED
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("registration : false. Person already registered");
        }
    }

    @GetMapping("/")
    public List<Person> getAllPersons() {
        return (List<Person>) personService.findAll();
    }

    /**
     * отобразить пользователя по его id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return personService.findById(id);
    }

    /**
     * удалить пользователя
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * обновить данные
     */

    @PatchMapping("/person/patch/{id}")
    public Person patch(@PathVariable int id, @Valid  @RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        return personService.patch(id, person);
    }


}
