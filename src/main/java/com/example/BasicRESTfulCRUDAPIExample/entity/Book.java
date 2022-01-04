package com.example.BasicRESTfulCRUDAPIExample.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "book")
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, length = 10)
    private String author;

    @Column(nullable = false)
    private int price;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateAuthor(String author) {
        this.author = author;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    @Builder
    public Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}
