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
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    @Transactional
    public String registrationBook(RegistrationBookDto dto) throws Exception {
        Optional<Book> book = bookRepository.findByBookId(dto.toEntity().getBookId());
        if (book.isPresent()){
            throw new DuplicateKeyException("이미 존재하는 책입니다.");
        }
        String title = bookRepository.save(dto.toEntity()).getTitle();
        return title;
    }

    @Transactional(readOnly = true)
    public List<ReadListBookDto> readListBookDto() throws Exception{
        return bookRepository.findAllByBookId().stream().map(book -> new ReadListBookDto(book)).collect(Collectors.toList());
    }

    @Transactional
    public void updateBookInfo(Long bookId, UpdateBookDto dto) throws Exception{
        Book book = bookRepository.findByBookId(bookId).orElseThrow(() -> new RuntimeException("책이 존재하지 않습니다."));
        if(dto.getTitle().equals(book.getTitle()) && dto.getAuthor().equals(book.getAuthor()) && dto.getPrice() == book.getPrice()){
            throw new RuntimeException("변경사항이 없습니다.");
        }
        book.updateTitle(dto.getTitle());
        book.updateAuthor(dto.getAuthor());
        book.updatePrice(dto.getPrice());
    }

    public ReadBookDetailDto readBookDetailDto(Long boardId) throws Exception{
        Book book = bookRepository.findByBookId(boardId).orElseThrow(() -> new RuntimeException("책이 존재하지 않습니다."));
        return new ReadBookDetailDto(book);
    }

    public void deleteBook (Long boardId) throws Exception{
        bookRepository.deleteById(boardId);
    }

}
