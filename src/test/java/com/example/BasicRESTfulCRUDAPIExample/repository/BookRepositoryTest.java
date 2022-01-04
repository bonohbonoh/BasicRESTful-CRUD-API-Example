package com.example.BasicRESTfulCRUDAPIExample.repository;

import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTest {

    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final int PRICE = 1000;
    private static final String UPDATE_TITLE = "update_title";
    private static final String UPDATE_AUTHOR = "author2";
    private static final int UPDATE_PRICE = 2000;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Order(1)
    public void createBookTest() {

        //given
        Book book = Book.builder()
                .title(TITLE)
                .author(AUTHOR)
                .price(PRICE)
                .build();
        //when
        Long bookId = bookRepository.save(book).getBookId();

        //then
        assertThat(bookId).isNotNull();

    }

    @Test
    @Order(2)
    public void readBookTitleTest() {

        //given
        String bookTitle = TITLE;

        //when
        Optional<Book> book = bookRepository.findAllByTitle(bookTitle);

        //then
        assertThat(book.get().getTitle()).isEqualTo(TITLE);
        assertThat(book.get().getAuthor()).isEqualTo(AUTHOR);
        assertThat(book.get().getPrice()).isEqualTo(PRICE);
    }

    @Test
    @Order(3)
    public void readBookListTest() {

        //given
        for (int i = 0; i < 10; i++) {
            Book book = Book.builder()
                    .title(TITLE + i)
                    .author(AUTHOR + i)
                    .price(PRICE + i)
                    .build();
            bookRepository.save(book);
        }

        //when
        List<Book> bookList = bookRepository.findAllByBookId();

        //then
        assertThat(bookList.size()).isEqualTo(11);
    }

    @Test
    @Order(4)
    public void updateTest() {

        //given
        Long bookId = 1L;
        Book book = bookRepository.findByBookId(bookId).get();

        //when
        book.updateTitle(UPDATE_TITLE);
        book.updateAuthor(UPDATE_AUTHOR);
        book.updatePrice(UPDATE_PRICE);
        bookRepository.save(book);

        //then
        assertThat(book.getTitle()).isEqualTo(UPDATE_TITLE);
        assertThat(book.getAuthor()).isEqualTo(UPDATE_AUTHOR);
        assertThat(book.getPrice()).isEqualTo(UPDATE_PRICE);
    }

    @Test
    @Order(5)
    public void deleteTest() {
        //given
        Long bookId = 1L;

        //when
        bookRepository.deleteById(bookId);

        //then
        assertThat(bookRepository.findByBookId(bookId)).isEmpty();
    }
}
