package com.example.BasicRESTfulCRUDAPIExample.repository;

import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    public Optional<Book> findByBookId(Long bookId);

    @Query ("select booklist from book booklist order by booklist.bookId desc") // BookId순서로 정렬
    public List<Book> findAllByBookId();
}
