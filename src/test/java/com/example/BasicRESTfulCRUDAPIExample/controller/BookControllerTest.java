package com.example.BasicRESTfulCRUDAPIExample.controller;

import com.example.BasicRESTfulCRUDAPIExample.dto.RegistrationBookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final int PRICE = 1000;
    private static final String URL = "/api/v1/book";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void postControllerTest() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(new RegistrationBookDto(TITLE, AUTHOR, PRICE));

        //when
        mvc.perform(
                        MockMvcRequestBuilders.post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

}
