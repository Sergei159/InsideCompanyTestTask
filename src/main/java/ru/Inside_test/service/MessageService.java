package ru.Inside_test.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.Inside_test.domain.Message;
import ru.Inside_test.dto.MessageDto;
import ru.Inside_test.repository.MessageRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     *
     * Метод проверяет, является ли тело сообщения null
     *  1. если сообщение "history", то пользователю возвращается вся его история сообщений
     *  2. если сообщение "history XXX", где XXX - Число,
     *  то пользователю возвращается XXX его сообщений,
     *  если ХХХ больше кол-ва сообщений, то возвращается вся история сообщений
     *  3. Иначе сообщение просто отправляется и сохраняется.
     */

    public ResponseEntity saveMessage(Message message) {
        if (message.getBody() == null) {
            throw new NullPointerException("Body message cannot be null");
        }
        List<Message> messageList;
        if ("history".equals(message.getBody())) {
            messageList = repository.findByPersonId(message.getPerson().getId());
            return ResponseEntity.ok().body(convertListToMessageDTO(messageList));
        } else if (containsHistoryRequestWithLimit(message)) {
            String[] splitValues = message.getBody().split(" ");
            messageList = repository.findMessageHistoryWithLimit(
                    message.getPerson().getId(), Integer.parseInt(splitValues[1]));
            return ResponseEntity.ok().body(convertListToMessageDTO(messageList));
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

    /**
     * Метод проверяет, что сообщение имеет вид
     * "history XXX", где XXX - число.
     * Пробел в теле сообщения обязателен
     */

    boolean containsHistoryRequestWithLimit(Message message) {
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

    /**
     *
     * Создает Лист MessageDTO из листа Message
     */

    private List<MessageDto> convertListToMessageDTO(List<Message> list) {
        return list.stream()
                .map(msg -> MessageDto.of(
                        msg.getPerson().getName(),
                        msg.getBody()))
                .collect(Collectors.toList());
    }

}
