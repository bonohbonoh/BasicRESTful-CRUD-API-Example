package com.example.BasicRESTfulCRUDAPIExample.dto;

import com.example.BasicRESTfulCRUDAPIExample.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class RegistrationBookDto {
    @NotEmpty
    @Size(min = 2, max = 40, message = "책글자는 2글자 ~ 40글자 사이입니다.")
    private String title;

    @NotEmpty
    @Size(min = 1, max = 10, message = "작가의 길이는 1글자 ~ 10글자 사이입니다.")
    private String author;

    @NotNull
    private int price;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .price(price)
                .build();
    }
}
