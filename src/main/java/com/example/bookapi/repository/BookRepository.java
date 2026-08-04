package com.example.bookapi.repository;

import com.example.bookapi.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    private final List<Book> books = new ArrayList<>();
    private long nextId = 4L;

    public BookRepository() {
        books.add(new Book(1L, "Spring Boot 入门", "张三"));
        books.add(new Book(2L, "Java 核心技术", "李四"));
        books.add(new Book(3L, "深入理解 Java 虚拟机", "王五"));
    }

    public List<Book> findAll() {
        return books;
    }

    public Book findById(Long id) {
        for (Book book : books) {
            if (id.equals(book.getId())) {
                return book;
            }
        }
        return null;
    }

    public Book save(Book book) {
        book.setId(nextId);
        nextId = nextId + 1;
        books.add(book);
        return book;
    }

    public void deleteById(Long id) {
        for (int index = 0; index < books.size(); index++) {
            Book book = books.get(index);
            if (id.equals(book.getId())) {
                books.remove(index);
                return;
            }
        }
    }
}
