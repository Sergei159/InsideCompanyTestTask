package ru.Inside_test.controller;

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
import ru.Inside_test.domain.Person;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestTaskApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private Gson gson;


    @Test
    @WithMockUser
    public void whenIsAlreadyExisted() throws Exception {
        Person person1 = Person.of("user2", "password2");
        this.mockMvc.perform(post("/person/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(person1)))
                .andExpect(status().isCreated());
        Person person2 = Person.of("user2", "password2");
        this.mockMvc.perform(post("/person/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(person2)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    public void whenPersonNotFound() throws Exception {
        this.mockMvc.perform(get("/person/200"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessageGet() throws Exception {
        this.mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void whenCreateAndFindPerson() throws Exception {
        Person person = Person.of("user1", "password1");
        this.mockMvc.perform(post("/person/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(person)))
                .andExpect(status().isCreated());
        this.mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());

    }

}
