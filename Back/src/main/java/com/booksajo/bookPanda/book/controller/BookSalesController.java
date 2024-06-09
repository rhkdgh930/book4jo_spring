package com.booksajo.bookPanda.book.controller;

import com.booksajo.bookPanda.book.domain.BookSales;
import com.booksajo.bookPanda.book.dto.*;
import com.booksajo.bookPanda.book.service.BookInfoService;
import com.booksajo.bookPanda.book.service.BookSalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookSalesController {

    private final BookInfoService bookInfoService;
    private final BookSalesService bookSalesService;

    @ResponseBody
    @PostMapping("/book")
    public ResponseEntity<List<BookInfo>> getBookInfo(@RequestBody NaverRequestVariableDto dto) throws JsonProcessingException {
        System.out.println(dto);
        return ResponseEntity.ok(bookInfoService.searchBookWithWebClient(dto));
    }

    @PostMapping("/getBookSales")
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
        return ResponseEntity.ok(bookSalesService.createBookSales(bookSalesRequest));
    }

    @DeleteMapping("/bookSales")
    public void deleteBookSales(@RequestParam("id") Long bookSalesId)
    {
        bookSalesService.deleteBookSales(bookSalesId);
    }


    @GetMapping("/bookSales")
    public ResponseEntity<?> getBookSales(@RequestParam("categoryId") Long categoryId,
                             @RequestParam(name = "page" , defaultValue = "0") int page ,
                             @RequestParam(name = "size" , defaultValue = "10") int size){

        Page<BookSales> bookSales= bookSalesService.getBookSalesByCategoryId(categoryId,page,size);
        return ResponseEntity.ok(bookSales.getContent());
    }

    @GetMapping("/bookSales/title")
    public ResponseEntity<?> getBookSalesTitleContainedWord(
            @RequestParam("keyword") String keyword,
            @RequestParam(name = "page" , defaultValue = "0") int page ,
            @RequestParam(name = "size" , defaultValue = "5") int size
    ){
        Page<BookSales> bookSales = bookSalesService.getBookSalesContainedWord(keyword,page,size);

        List<BookTitleInfo> bookTitleInfos = new ArrayList<>();

        for(BookSales book:bookSales.get().toList()){
            bookTitleInfos.add(new BookTitleInfo(book.getId(),book.getBookInfo().getTitle()));
        }

        return ResponseEntity.ok(bookTitleInfos);
    }
}
