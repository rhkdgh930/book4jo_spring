package com.booksajo.bookPanda.product.exception.handler;

import com.booksajo.bookPanda.product.exception.ErrorCode;
import com.booksajo.bookPanda.product.exception.dto.ErrorResponse;
import com.booksajo.bookPanda.product.exception.errorCode.NaverAPIErrorCode;
import com.booksajo.bookPanda.product.exception.exception.BookSalesException;
import com.booksajo.bookPanda.product.exception.exception.NaverAPIException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NaverAPIException.class) // ①
    public ResponseEntity<Object> handleNaverAPIException(NaverAPIException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(BookSalesException.class) // ①
    public ResponseEntity<Object> handleBookSalesException(BookSalesException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, errorCode.getMessage()));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 커스텀 예외 생성 및 반환
        throw new NaverAPIException(NaverAPIErrorCode.INCORRECT_QUERY_REQ_ERROR);
    }
}
