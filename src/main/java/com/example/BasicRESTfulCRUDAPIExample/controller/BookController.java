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
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @PostMapping(value = "")
    public ResponseEntity bookSave(@RequestBody @Valid RegistrationBookDto dto) throws Exception {
        String title = bookService.registrationBook(dto);
        if (!title.equals("")) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/readall")
    public ResponseEntity<List<ReadListBookDto>> readListBookDtoResponseEntity() throws Exception {
        List<ReadListBookDto> readListBookDtoList = bookService.readListBookDto();
        return new ResponseEntity<List<ReadListBookDto>>(readListBookDtoList, HttpStatus.OK);
    }

    @PutMapping(value = "/{bookId}")
    public ResponseEntity<UpdateBookDto> updateBookDtoResponseEntity(@PathVariable Long bookId, @RequestBody UpdateBookDto dto) throws Exception {
        bookService.updateBookInfo(bookId, dto);
        return new ResponseEntity<UpdateBookDto>(HttpStatus.OK);
    }

    @GetMapping(value = "/{bookId}")
    public ResponseEntity<ReadBookDetailDto> readBookDetailDtoResponseEntity(@PathVariable Long bookId) throws Exception {
        ReadBookDetailDto readBookDetailDto = bookService.readBookDetailDto(bookId);
        return new ResponseEntity<ReadBookDetailDto>(readBookDetailDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "{bookId}")
    public ResponseEntity bookDelete(@PathVariable Long bookId) throws Exception{
        bookService.deleteBook(bookId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
