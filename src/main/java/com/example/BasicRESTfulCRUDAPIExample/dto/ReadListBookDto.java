package com.example.BasicRESTfulCRUDAPIExample.dto;

import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@RequiredArgsConstructor
@ToString
public class ReadListBookDto {

    private String title;

    private String author;

    public ReadListBookDto(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }
}
