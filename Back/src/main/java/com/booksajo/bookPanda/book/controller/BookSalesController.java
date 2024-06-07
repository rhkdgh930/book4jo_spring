package com.booksajo.bookPanda.book.controller;

import com.booksajo.bookPanda.book.dto.*;
import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.book.service.BookInfoService;
import com.booksajo.bookPanda.book.service.BookSalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookSalesController {

    private final BookInfoService bookInfoService;
    private final BookSalesService bookSalesService;

    @ResponseBody
    @GetMapping("/book")
    public ResponseEntity<List<BookInfo>> getBookInfo(@Valid NaverRequestVariableDto dto) throws JsonProcessingException {
        return ResponseEntity.ok(bookInfoService.searchBookWithWebClient(dto));
    }

    @GetMapping("/bookSales")
    public ResponseEntity<ResponseBookSales> getBookSales(@RequestParam("id") Long bookSalesId)
    {
        return ResponseEntity.ok(bookSalesService.getBookSales(bookSalesId));
    }

    @GetMapping("bookSaless")
    public ResponseEntity<List<BookSales>> getBookSaless()
    {
        return ResponseEntity.ok(bookSalesService.getBookSalesList());
    }

    @PatchMapping("/bookSales")
    public ResponseEntity<BookSales> getBookSales(@RequestParam("id") Long bookSalesId, @RequestBody BookSalesRequest bookSalesRequest)
    {
        return ResponseEntity.ok(bookSalesService.patchBookSales(bookSalesId, bookSalesRequest));
    }
    @PostMapping("/bookSales")
    public ResponseEntity<BookSales> createBookSales(@RequestBody BookSalesRequest bookSalesRequest)
    {
        System.out.println(bookSalesRequest.getSalesInfoDto().getCategoryId());
        return ResponseEntity.ok(bookSalesService.createBookSales(bookSalesRequest));
    }

    @DeleteMapping("/bookSales")
    public void deleteBookSales(@RequestParam("id") Long bookSalesId)
    {
        bookSalesService.deleteBookSales(bookSalesId);
    }

}
