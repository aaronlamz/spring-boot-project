package com.example.bookapi.exception;

import com.example.bookapi.common.ErrorCode;

public class BookNotFoundException extends BusinessException {

    public BookNotFoundException(Long id) {
        super(ErrorCode.BOOK_NOT_FOUND, "图书不存在，编号 " + id);
    }
}
