package com.example.BasicRESTfulCRUDAPIExample.service;

import com.example.BasicRESTfulCRUDAPIExample.dto.ReadBookDetailDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.ReadListBookDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.RegistrationBookDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.UpdateBookDto;
import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import com.example.BasicRESTfulCRUDAPIExample.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTest {

    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final int PRICE = 1000;
    private static final String UPDATE_TITLE = "update_title";
    private static final String UPDATE_AUTHOR = "author2";
    private static final int UPDATE_PRICE = 2000;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    public void initBook(){
        Book book = Book.builder()
                .title(TITLE)
                .author(AUTHOR)
                .price(1000)
                .build();
        bookRepository.save(book);
    }

    public void deleteAll(){
        bookRepository.deleteAll();
    }

    @Test
    public void registrationServiceTest() throws Exception {
        deleteAll();

        //given
        RegistrationBookDto dto = new RegistrationBookDto(TITLE, AUTHOR, PRICE);

        //when
        Long bookId = bookService.registrationBook(dto);

        //then
        assertThat(bookId).isNotNull();

    }

    @Test
    public void readDetailServiceTest() throws Exception{

        //given
        Optional<Book> book = bookRepository.findByBookId(1L);

        //when
        ReadBookDetailDto dto = bookService.readBookDetailDto(book.get().getBookId());

        //then
        assertThat(dto).isNotNull();

    }

    @Test
    public void updateServiceTest() throws Exception{
        //given
        Long bookId = 1L;

        //when
        UpdateBookDto dto = new UpdateBookDto(UPDATE_TITLE,UPDATE_AUTHOR,UPDATE_PRICE);
        Book book = bookRepository.findByBookId(bookId).get();
        book.updateTitle(UPDATE_TITLE);
        book.updateAuthor(UPDATE_AUTHOR);
        book.updatePrice(UPDATE_PRICE);
        //then
        assertThat(book.getTitle()).isEqualTo(UPDATE_TITLE);
        assertThat(book.getAuthor()).isEqualTo(UPDATE_AUTHOR);
        assertThat(book.getPrice()).isEqualTo(UPDATE_PRICE);
    }

    @Test
    public void readListServiceTest() throws Exception{

        //given
        for(int i=0;i<10;i++){
            bookService.registrationBook(new RegistrationBookDto(TITLE+i,AUTHOR+i,PRICE+i));
        }

        //when
        List<ReadListBookDto> dto = bookService.readListBookDto();

        //then
        assertThat(dto.size()).isEqualTo(11);
    }

    @Test
    public void deleteServiceTest() throws Exception{

        //given
        Long bookId = 1L;

        //when
        bookService.deleteBook(bookId);

        //then
        assertThat(bookRepository.findByBookId(bookId)).isEmpty();
    }
}
