package ru.Inside_test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.Inside_test.TestTaskApplication;
import ru.Inside_test.domain.Message;
import ru.Inside_test.domain.Person;
import ru.Inside_test.dto.MessageDto;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestTaskApplication.class)
@AutoConfigureMockMvc
public class MessageControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/message/"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void whenMessageNotFound() throws Exception {
        this.mockMvc.perform(get("/message/200"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}
