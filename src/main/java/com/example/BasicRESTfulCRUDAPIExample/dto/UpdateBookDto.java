package com.example.BasicRESTfulCRUDAPIExample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateBookDto {

    private String title;

    private String author;

    private int price;

}
