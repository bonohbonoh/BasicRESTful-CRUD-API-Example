package com.example.BasicRESTfulCRUDAPIExample.service;

import com.example.BasicRESTfulCRUDAPIExample.dto.ReadBookDetailDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.ReadListBookDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.RegistrationBookDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.UpdateBookDto;
import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import com.example.BasicRESTfulCRUDAPIExample.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public Long registrationBook(RegistrationBookDto dto) throws Exception {
        Optional<Book> book = bookRepository.findAllByTitle(dto.toEntity().getTitle());
        if (book.isPresent()) {
            throw new DuplicateKeyException("이미 존재하는 책입니다.");
        }
        Long bookId = bookRepository.save(dto.toEntity()).getBookId();
        return bookId;
    }

    @Transactional(readOnly = true)
    public List<ReadListBookDto> readListBookDto() throws Exception {
        return bookRepository.findAllByBookId().stream().map(book -> new ReadListBookDto(book)).collect(Collectors.toList());
    }

    @Transactional
    public Long updateBookInfo(Long bookId, UpdateBookDto dto) throws Exception {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> new RuntimeException("책이 존재하지 않습니다."));
        if (dto.getTitle().equals(book.getTitle()) && dto.getAuthor().equals(book.getAuthor()) && dto.getPrice() == book.getPrice()) {
            throw new RuntimeException("변경사항이 없습니다.");
        }
        book.updateTitle(dto.getTitle());
        book.updateAuthor(dto.getAuthor());
        book.updatePrice(dto.getPrice());
        Long saveBookId = bookRepository.save(book).getBookId();
        return saveBookId;
    }

    public Long readBookDetailDto(Long bookId) throws Exception {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> new RuntimeException("책이 존재하지 않습니다."));
        return bookId;
    }

    public Long deleteBook(Long bookId) throws Exception {
        bookRepository.deleteById(bookId);
        return bookId;
    }

}
