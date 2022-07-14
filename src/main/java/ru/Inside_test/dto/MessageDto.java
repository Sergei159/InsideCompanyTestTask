package ru.Inside_test.dto;

/**
 * DTO для отображения сообщении
 */
public class MessageDto {
    private String personName;
    private String body;

    public static MessageDto of(String personName, String body) {
        MessageDto messageDto = new MessageDto();
        messageDto.personName = personName;
        messageDto.body = body;
        return messageDto;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
