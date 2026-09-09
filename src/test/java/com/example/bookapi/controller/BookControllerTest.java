package com.example.bookapi.controller;

import com.example.bookapi.config.ApiResponseBodyAdvice;
import com.example.bookapi.dto.BookMapper;
import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.model.Book;
import com.example.bookapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import({BookMapper.class, ApiResponseBodyAdvice.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void findAllWrapsListInsideData() throws Exception {
        given(bookService.findAll()).willReturn(Arrays.asList(
                new Book(1L, "Spring Boot 入门", "张三"),
                new Book(2L, "Java 核心技术", "李四")));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("成功"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("Spring Boot 入门"));
    }

    @Test
    void findByIdWrapsSingleBookInsideData() throws Exception {
        given(bookService.findById(1L)).willReturn(new Book(1L, "Spring Boot 入门", "张三"));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Spring Boot 入门"));
    }

    @Test
    void createReturnsCodeZeroWithGeneratedId() throws Exception {
        given(bookService.create(any(Book.class)))
                .willReturn(new Book(4L, "数据库入门", "赵六"));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"数据库入门\",\"author\":\"赵六\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(4));
    }

    @Test
    void createIgnoresIdInRequestBody() throws Exception {
        given(bookService.create(any(Book.class)))
                .willReturn(new Book(4L, "数据库入门", "赵六"));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":123,\"title\":\"数据库入门\",\"author\":\"赵六\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(4));

        ArgumentCaptor<Book> sentToService = ArgumentCaptor.forClass(Book.class);
        verify(bookService).create(sentToService.capture());
        assertThat(sentToService.getValue().getId()).isNull();
    }

    @Test
    void blankTitleReturnsParamInvalidCodeAndSkipsService() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"   \",\"author\":\"赵六\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1001))
                .andExpect(jsonPath("$.msg").value("请求参数不正确"))
                .andExpect(jsonPath("$.data.title").value("书名不能为空"));

        verify(bookService, never()).create(any(Book.class));
    }

    @Test
    void missingBookReturnsBookNotFoundCode() throws Exception {
        given(bookService.findById(99L)).willThrow(new BookNotFoundException(99L));

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1002))
                .andExpect(jsonPath("$.msg").value("图书不存在，编号 99"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void deleteReturnsCodeZeroWithoutData() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(bookService).delete(1L);
    }

    @Test
    void pathVariableThatIsNotANumberReturnsParamInvalidCode() throws Exception {
        mockMvc.perform(get("/api/books/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1001));

        verify(bookService, never()).findById(anyLong());
    }

    @Test
    void brokenJsonReturnsParamInvalidCode() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1001));

        verify(bookService, never()).create(any(Book.class));
    }
}
