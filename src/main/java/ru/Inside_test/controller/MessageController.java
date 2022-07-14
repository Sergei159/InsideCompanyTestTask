package ru.Inside_test.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.Inside_test.domain.Message;
import ru.Inside_test.service.MessageService;
import ru.Inside_test.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final PersonService personService;

    public MessageController(MessageService messageService, PersonService personService) {
        this.messageService = messageService;
        this.personService = personService;
    }

    /**
     * Вывести все сообщения
     */
    @GetMapping("/")
    public List<Message> findAllMessage() {
        return (List<Message>) messageService.findAll();
    }

    /**
     * Вывести сообщение по id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Message> findMessageById(@PathVariable int id) {
        Optional<Message> message = messageService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Message()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * Вывести все сообщения пользователя по id
     */

    @GetMapping("/person/{id}")
    public List<Message> findMessagesByPersonId( @PathVariable int id) {
        List<Message> messages = messageService.findMessageByPersonId(id);
        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Messages were not found");
        }
        return messages;
    }


    /**
     * Опубликовать сообщение
     */
    @PostMapping("/")
    public ResponseEntity save(
            @Valid @RequestBody Message message,
            HttpServletRequest req) {
        String userName = req.getSession().getAttribute("userName").toString();
        Optional<Message> resultMessage = Optional.ofNullable(message);
        if (resultMessage.isPresent()) {
            message.setPerson(personService.findByName(userName));
            return messageService.saveMessage(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message cannot be null!");
        }

    }

    /**
     * Обновить сообщение
     */
    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Message message) {
        Optional<Message> result = Optional.ofNullable(message);
        if (result.isPresent()) {
            this.messageService.saveMessage(message);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удалить сообщение
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

    /**
     * обновить данные
     */

    @PatchMapping("/patch/{id}")
    public Message patch(@PathVariable int id, @Valid @RequestBody Message message) throws InvocationTargetException, IllegalAccessException {
        return messageService.patch(id, message);
    }
}
