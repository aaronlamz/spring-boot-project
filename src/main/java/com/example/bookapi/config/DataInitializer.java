package com.example.bookapi.config;

import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book(null, "Spring Boot 入门", "张三"));
            bookRepository.save(new Book(null, "Java 核心技术", "李四"));
            bookRepository.save(new Book(null, "深入理解 Java 虚拟机", "王五"));
        }
    }
}
