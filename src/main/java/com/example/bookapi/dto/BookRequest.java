package com.example.bookapi.dto;

import javax.validation.constraints.NotBlank;

/**
 * 新增和修改图书时，客户端允许提交的字段。
 */
public class BookRequest {

    @NotBlank(message = "书名不能为空")
    private String title;

    @NotBlank(message = "作者不能为空")
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
