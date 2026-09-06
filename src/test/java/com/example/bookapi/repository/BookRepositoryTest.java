package com.example.bookapi.repository;

import com.example.bookapi.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void databaseStartsEmpty() {
        assertThat(bookRepository.count()).isZero();
    }

    @Test
    void saveGeneratesId() {
        Book saved = bookRepository.save(new Book(null, "Spring Boot 入门", "张三"));

        assertThat(saved.getId()).isNotNull();
        assertThat(bookRepository.count()).isEqualTo(1);
    }

    @Test
    void findByIdReturnsEmptyOptionalWhenRowIsMissing() {
        Optional<Book> found = bookRepository.findById(99L);

        assertThat(found).isEmpty();
    }

    @Test
    void deleteByIdRemovesRow() {
        Book saved = bookRepository.save(new Book(null, "Java 核心技术", "李四"));

        bookRepository.deleteById(saved.getId());

        assertThat(bookRepository.existsById(saved.getId())).isFalse();
    }
}
