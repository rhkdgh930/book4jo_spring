package com.booksajo.bookPanda.product.controller;

import com.booksajo.bookPanda.product.dto.*;
import com.booksajo.bookPanda.product.entity.BookSales;
import com.booksajo.bookPanda.product.service.BookInfoService;
import com.booksajo.bookPanda.product.service.BookSalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookInfoController {

    private final BookInfoService bookInfoService;
    private final BookSalesService bookSalesService;

    @ResponseBody
    @GetMapping("/book")
    public ResponseEntity<List<BookInfo>> getBookInfo(@Valid @RequestBody NaverRequestVariableDto dto) throws JsonProcessingException {
        return ResponseEntity.ok(bookInfoService.searchBookWithWebClient(dto));
    }

    @GetMapping("/bookSales")
    public ResponseEntity<BookSales> getBookSales(@RequestParam("id") Long bookSalesId)
    {
        return ResponseEntity.ok(bookSalesService.getBookSales(bookSalesId));
    }


    @PatchMapping("/bookSales")
    public ResponseEntity<BookSales> getBookSales(@RequestParam("id") Long bookSalesId, @RequestBody BookSalesRequest bookSalesRequest)
    {
        return ResponseEntity.ok(bookSalesService.patchBookSales(bookSalesId, bookSalesRequest));
    }
    @PostMapping("/bookSales")
    public ResponseEntity<BookSales> createBookSales(@RequestBody BookSalesRequest bookSalesRequest)
    {
        return ResponseEntity.ok(bookSalesService.createBookSales(bookSalesRequest));
    }

    @DeleteMapping("/bookSales")
    public void deleteBookSales(@RequestParam("id") Long bookSalesId)
    {
        bookSalesService.deleteBookSales(bookSalesId);
    }

}
