package com.example.bookapi.controller;

import com.example.bookapi.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return new Book(id, "Spring Boot 入门", "张三");
    }
}

