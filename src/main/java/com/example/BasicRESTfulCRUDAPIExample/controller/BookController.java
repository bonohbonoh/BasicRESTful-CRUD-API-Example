package com.example.BasicRESTfulCRUDAPIExample.controller;

import com.example.BasicRESTfulCRUDAPIExample.dto.ReadBookDetailDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.ReadListBookDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.RegistrationBookDto;
import com.example.BasicRESTfulCRUDAPIExample.dto.UpdateBookDto;
import com.example.BasicRESTfulCRUDAPIExample.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @PostMapping(value = "")
    public ResponseEntity<Long> bookSave(@RequestBody @Valid RegistrationBookDto dto) throws Exception {
        return new ResponseEntity<Long>(bookService.registrationBook(dto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/readall")
    public ResponseEntity<List<ReadListBookDto>> readListBookDtoResponseEntity() throws Exception {
        return new ResponseEntity<List<ReadListBookDto>>(bookService.readListBookDto(), HttpStatus.OK);
    }

    @PutMapping(value = "/{bookId}")
    public ResponseEntity<Long> updateBookDtoResponseEntity(@PathVariable Long bookId, @Valid @RequestBody UpdateBookDto dto) throws Exception {
        return new ResponseEntity<Long>(bookService.updateBookInfo(bookId, dto), HttpStatus.OK);
    }

    @GetMapping(value = "/{bookId}")
    public ResponseEntity<Long> readBookDetailDtoResponseEntity(@PathVariable Long bookId) throws Exception {
        return new ResponseEntity<Long>(bookService.readBookDetailDto(bookId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{bookId}")
    public ResponseEntity<Long> bookDelete(@PathVariable Long bookId) throws Exception {
        return new ResponseEntity<Long>(bookService.deleteBook(bookId), HttpStatus.OK);
    }
}
