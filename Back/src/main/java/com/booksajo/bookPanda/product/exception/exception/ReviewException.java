package com.booksajo.bookPanda.product.exception.exception;

import com.booksajo.bookPanda.product.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewException extends RuntimeException{
    private final ErrorCode errorCode;
}
