package ru.Inside_test.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Inside_test.TestTaskApplication;
import ru.Inside_test.domain.Message;
import ru.Inside_test.domain.Person;

@SpringBootTest(classes = TestTaskApplication.class)
public class MessageServiceTest {

    @Autowired
    private MessageService service;

    @Test
    public void whenContainsHistoryRequestWithLimit() {
        Person person = Person.of("testUser", "pass");
        Message message = Message.of("history 5", person);
        Assert.assertTrue(service.containsHistoryRequestWithLimit(message));
    }

    @Test
    public void whenDoesNotContainHistoryRequest() {
        Person person = Person.of("testUser", "pass");
        Message message = Message.of("history5", person);
        Assert.assertFalse(service.containsHistoryRequestWithLimit(message));
    }

    @Test
    public void whenDoesNotContainDigit() {
        Person person = Person.of("testUser", "pass");
        Message message = Message.of("history g", person);
        Assert.assertFalse(service.containsHistoryRequestWithLimit(message));
    }
}
