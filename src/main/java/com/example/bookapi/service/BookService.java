package com.example.bookapi.service;

import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book create(Book book) {
        book.setId(null);
        return bookRepository.save(book);
    }

    public Book update(Long id, Book updatedBook) {
        Book book = findById(id);
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}
