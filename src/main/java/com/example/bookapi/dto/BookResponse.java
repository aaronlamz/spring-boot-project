package com.example.bookapi.dto;

/**
 * 接口返回给客户端的图书字段。
 */
public class BookResponse {

    private Long id;
    private String title;
    private String author;

    public BookResponse(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
