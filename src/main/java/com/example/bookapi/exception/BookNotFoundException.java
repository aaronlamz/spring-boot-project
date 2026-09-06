package com.example.bookapi.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("图书不存在，编号 " + id);
    }
}
