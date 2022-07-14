package ru.Inside_test.service;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.Inside_test.domain.Message;
import ru.Inside_test.repository.MessageRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements Store {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public List<Message> findAll() {
        return (List<Message>) repository.findAll();
    }

    public Optional<Message> findById(int id) {
        return repository.findById(id);
    }


    public List<Message> findMessageByPersonId(int personId) {
        return repository.findByPersonId(personId);
    }

    public ResponseEntity saveMessage(Message message) {
        List<Message> resultList;
        if ("history".equals(message.getBody())) {
            resultList = repository.findByPersonId(message.getPerson().getId());
            return ResponseEntity.ok().body(resultList);
        } else if (containsHistoryRequest(message)) {
            String[] splitValues = message.getBody().split(" ");
            resultList = repository.findMessageHistoryWithLimit(
                    message.getPerson().getId(), Integer.parseInt(splitValues[1]));
            System.out.println(resultList.toString());
            return ResponseEntity.ok().body(resultList);
        } else {
            repository.save(message);
            return ResponseEntity.ok().build();
        }
    }

    public void deleteMessage(int id) {
        repository.deleteById(id);
    }

    public Message patch(int id, Message message) throws InvocationTargetException, IllegalAccessException {
        return (Message) patch(repository, id, message);
    }


    private boolean containsHistoryRequest(Message message) {
        boolean result = false;
        String body = message.getBody();
        if (!body.isBlank() && body.startsWith("history")) {
            String[] splitValues = body.split(" ");
            if (splitValues.length > 1
                && splitValues[1] != null
                && splitValues[1].matches("\\d+")) {
                        result = true;
            }
        }
        return result;
    }

}
