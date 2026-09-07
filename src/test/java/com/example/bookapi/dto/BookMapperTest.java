package com.example.bookapi.dto;

import com.example.bookapi.model.Book;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    private final BookMapper bookMapper = new BookMapper();

    @Test
    void toEntityCopiesTitleAndAuthorButLeavesIdNull() {
        BookRequest request = new BookRequest();
        request.setTitle("数据库入门");
        request.setAuthor("赵六");

        Book book = bookMapper.toEntity(request);

        assertThat(book.getId()).isNull();
        assertThat(book.getTitle()).isEqualTo("数据库入门");
        assertThat(book.getAuthor()).isEqualTo("赵六");
    }

    @Test
    void toResponseCopiesAllThreeFields() {
        BookResponse response = bookMapper.toResponse(new Book(1L, "Spring Boot 入门", "张三"));

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Spring Boot 入门");
        assertThat(response.getAuthor()).isEqualTo("张三");
    }

    @Test
    void toResponseListKeepsOrder() {
        List<Book> books = Arrays.asList(
                new Book(1L, "Spring Boot 入门", "张三"),
                new Book(2L, "Java 核心技术", "李四"));

        List<BookResponse> responses = bookMapper.toResponseList(books);

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getTitle()).isEqualTo("Spring Boot 入门");
        assertThat(responses.get(1).getTitle()).isEqualTo("Java 核心技术");
    }

    @Test
    void toResponseListReturnsEmptyListForEmptyInput() {
        List<BookResponse> responses = bookMapper.toResponseList(Collections.<Book>emptyList());

        assertThat(responses).isEmpty();
    }
}
