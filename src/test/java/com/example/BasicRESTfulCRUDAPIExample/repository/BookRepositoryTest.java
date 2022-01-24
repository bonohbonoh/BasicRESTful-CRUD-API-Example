package com.example.BasicRESTfulCRUDAPIExample.repository;

import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookRepositoryTest {

    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final int PRICE = 1000;
    private static final String UPDATE_TITLE = "update_title";
    private static final String UPDATE_AUTHOR = "author2";
    private static final int UPDATE_PRICE = 2000;

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
    public void createBookTest() {
        deleteAll();

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
    public void readBookTitleTest() {

        //given
        String bookTitle = TITLE;

        //when
        Book book = bookRepository.findByTitle(bookTitle).orElseThrow(() -> new RuntimeException("책이 존재하지 않습니다."));

        //then
        assertThat(book.getTitle()).isEqualTo(TITLE);
        assertThat(book.getAuthor()).isEqualTo(AUTHOR);
        assertThat(book.getPrice()).isEqualTo(PRICE);
    }

    @Test
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
    public void updateTest() {

        //given
        Long bookId = 1L;
        Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> new RuntimeException("책이 존재하지 않습니다."));

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
    public void deleteTest() {
        //given
        Long bookId = 1L;

        //when
        bookRepository.deleteById(bookId);

        //then
        assertThat(bookRepository.findByBookId(bookId)).isEmpty();
    }
}
