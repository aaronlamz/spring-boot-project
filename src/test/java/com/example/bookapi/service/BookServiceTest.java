package com.example.bookapi.service;

import com.example.bookapi.common.ErrorCode;
import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void findByIdReturnsBookWhenIdExists() {
        Book stored = new Book(1L, "Spring Boot 入门", "张三");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(stored));

        Book found = bookService.findById(1L);

        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getTitle()).isEqualTo("Spring Boot 入门");
    }

    @Test
    void findByIdThrowsWhenIdDoesNotExist() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(99L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("图书不存在，编号 99")
                .extracting(exception -> ((BookNotFoundException) exception).getErrorCode())
                .isEqualTo(ErrorCode.BOOK_NOT_FOUND);
    }

    @Test
    void createIgnoresIdSentByClient() {
        when(bookRepository.save(any(Book.class))).thenAnswer(call -> call.getArgument(0));

        bookService.create(new Book(123L, "数据库入门", "赵六"));

        ArgumentCaptor<Book> saved = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(saved.capture());
        assertThat(saved.getValue().getId()).isNull();
    }

    @Test
    void updateWritesNewTitleAndAuthor() {
        Book stored = new Book(1L, "旧书名", "张三");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(stored));
        when(bookRepository.save(any(Book.class))).thenAnswer(call -> call.getArgument(0));

        Book updated = bookService.update(1L, new Book(null, "新书名", "李四"));

        assertThat(updated.getId()).isEqualTo(1L);
        assertThat(updated.getTitle()).isEqualTo("新书名");
        assertThat(updated.getAuthor()).isEqualTo("李四");
    }

    @Test
    void deleteDoesNotTouchRepositoryWhenIdDoesNotExist() {
        when(bookRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> bookService.delete(99L))
                .isInstanceOf(BookNotFoundException.class);

        verify(bookRepository, never()).deleteById(anyLong());
    }
}
