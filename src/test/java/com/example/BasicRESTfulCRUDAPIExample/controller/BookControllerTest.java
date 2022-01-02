package com.example.BasicRESTfulCRUDAPIExample.controller;

import com.example.BasicRESTfulCRUDAPIExample.dto.RegistrationBookDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.UpdateBookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTest {

    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final int PRICE = 1000;
    private static final String UPDATE_TITLE = "update_title";
    private static final String UPDATE_AUTHOR = "author2";
    private static final int UPDATE_PRICE = 2000;
    private static final String URL = "/api/v1/book/";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookId").exists())
                .andDo(print());
    }

    @Test
    @Order(4)
    public void putControllerTest() throws Exception {

        //given
        Long bookId = 1L;
        String content = objectMapper.writeValueAsString(new UpdateBookDto(UPDATE_TITLE, UPDATE_AUTHOR, UPDATE_PRICE));
        //when
        mvc.perform(
                        MockMvcRequestBuilders
                                .put(URL + "{bookId}", bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    public void getControllerTest() throws Exception {

        //given
        Long bookId = 1L;
        //when
        mvc.perform(
                        MockMvcRequestBuilders.get(URL + "{bookId}", bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Order(3)
    public void getAllControllerTest() throws Exception {

        //when
        mvc.perform(
                        MockMvcRequestBuilders.get(URL + "readall")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Order(5)
    public void deleteControllerTest() throws Exception {

        //given
        Long bookId = 1L;
        //when
        mvc.perform(
                        MockMvcRequestBuilders.delete(URL + "{bookId}", bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

}
