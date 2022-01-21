package com.example.BasicRESTfulCRUDAPIExample.controller;

import com.example.BasicRESTfulCRUDAPIExample.dto.RegistrationBookDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.UpdateBookDto;
import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import com.example.BasicRESTfulCRUDAPIExample.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookController bookController;

    @BeforeAll
    public void settingMvcBuilder() {
        mvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .addFilter(new CharacterEncodingFilter("utf-8", true))
                .build();
    }

    @BeforeAll
    public void initBook(){
        Book book = Book.builder()
                .title("init_title")
                .author("name")
                .price(1000)
                .build();
        bookRepository.save(book);
    }

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

    @Test
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
