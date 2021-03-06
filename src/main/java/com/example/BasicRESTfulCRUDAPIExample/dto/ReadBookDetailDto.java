package com.example.BasicRESTfulCRUDAPIExample.dto;

import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ReadBookDetailDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotNull
    private int price;

    public ReadBookDetailDto(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.price = book.getPrice();
    }
}
