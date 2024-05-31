package com.booksajo.bookPanda.book.dto;

import com.booksajo.bookPanda.book.entity.BookSales;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSalesDto {
    private Integer visitCount;
    private Integer sellCount;
    private Integer stock;
    private BookInfo bookInfo;

    public BookSales toEntity()
    {
        return BookSales.builder()
                .visitCount(this.visitCount)
                .sellCount(this.sellCount)
                .stock(this.stock)
                .bookInfo(this.bookInfo)
                .build();
    }
}
